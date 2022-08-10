package com.order.summary.transformer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Order {
    @JsonProperty("id")
    private int id;

    @JsonProperty("vendor")
    private String vendor;

    @JsonProperty("date")
    private String date;

    @JsonProperty("customer")
    private String customerId;

    @JsonProperty("order")
    private List<OrderDetail> details;

    @Data
    @Builder
    public static class OrderDetail {
        @JsonProperty("item")
        private String item;

        @JsonProperty("quantity")
        private int quantity;

        @JsonProperty("price")
        private long price;

        @JsonProperty("revenue")
        private long revenue;
    }
}


