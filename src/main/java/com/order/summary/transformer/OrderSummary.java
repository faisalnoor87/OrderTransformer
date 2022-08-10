package com.order.summary.transformer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {
    private int id;
    private String vendor;
    private String date;
    private Customer customer;
    private Map<String, Order> order;

    @Data
    @Builder
    public static class Customer {
        private String id;
        private String name;
        private String address;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Order {
        private Integer quantity;
        private long price;
    }
}


