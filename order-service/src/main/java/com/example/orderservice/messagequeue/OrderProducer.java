package com.example.orderservice.messagequeue;

import com.example.orderservice.jpa.OrderDto;
import com.example.orderservice.vo.Field;
import com.example.orderservice.vo.Payload;
import com.example.orderservice.vo.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
@Slf4j
public class OrderProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields= Arrays.asList(
            new Field("string", true, "order_id"),
            new Field("string", true, "user_id"),
            new Field("string", true, "product_id"),
            new Field("int32", true,"qty" ),
            new Field("int32", true, "total_price"),
            new Field("int32", true, "unit_price")
    );
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();


    public OrderDto send(String kafkaTopic, OrderDto orderDto){
        Payload payload = Payload.builder()
                
    }
}
