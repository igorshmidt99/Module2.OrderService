package org.example.discount;

import org.example.model.CustomerType;
import org.example.model.Order;

public class PremiumDiscount implements DiscountsStrategy {
    @Override
    public CustomerType supports() {
        return CustomerType.PREMIUM;
    }

    @Override
    public double calculate(Order order) {
        return order.getAmount() * 0.10;
    }
}
