package com.example.orderservice.vo;

import java.io.Serializable;

public class KafkaOrderDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
