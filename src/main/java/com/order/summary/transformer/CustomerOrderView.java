package com.order.summary.transformer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CustomerOrderView {
    @JsonProperty("customers")
    public List<Customer> customers;

    @JsonProperty("orders")
    public List<Order> orders;
}
