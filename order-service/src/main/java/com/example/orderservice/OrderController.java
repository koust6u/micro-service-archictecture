
package com.example.orderservice;

import com.example.orderservice.jpa.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.RequestOrder;
import com.example.orderservice.jpa.ResponseOrder;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service/")
public class OrderController {
    private final OrderProducer orderProducer;
    private final KafkaProducer kafkaProducer;
    private final OrderService service;
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder orderDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        /*JPA
        OrderDto createDto = service.createOrder(orderDto);
        ResponseOrder returnValue = modelMapper.map(createDto, ResponseOrder.class); */

        /*kafka*/
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
        ResponseOrder returnValue = modelMapper.map(orderDto, ResponseOrder.class);
        /*Send on order to the Kafka*/
        kafkaProducer.send("example-order-topic", orderDto);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>>getOrder(@PathVariable("userId") String userId){
        Iterable<OrderEntity> orderList = service.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v ->{
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
