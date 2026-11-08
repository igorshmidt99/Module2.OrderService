package org.example.discount;

import org.example.model.CustomerType;
import org.example.model.Order;

public interface DiscountsStrategy {
    CustomerType supports();
    double calculate(Order order);
}
