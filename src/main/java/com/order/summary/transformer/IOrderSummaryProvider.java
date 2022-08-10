package com.order.summary.transformer;

import java.io.IOException;
import java.util.List;


public interface IOrderSummaryProvider {
    List<OrderSummary> getOrderSummary() throws IOException;

}
