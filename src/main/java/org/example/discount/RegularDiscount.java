package org.example.discount;

import org.example.model.CustomerType;
import org.example.model.Order;

public class RegularDiscount implements DiscountsStrategy {
    @Override
    public CustomerType supports() {
        return CustomerType.REGULAR;
    }

    @Override
    public double calculate(Order order) {
        return 0.0;
    }
}
