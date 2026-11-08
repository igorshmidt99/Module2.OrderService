package org.example.commission;

import org.example.model.Order;
import org.example.model.PaymentType;

public class CardCommission implements CommissionStrategy {
    @Override
    public PaymentType supports() {
        return PaymentType.CARD;
    }

    @Override
    public double calculate(Order order) {
        return order.getAmount() * 0.02;
    }
}
