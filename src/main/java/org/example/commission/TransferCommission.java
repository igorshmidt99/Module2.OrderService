package org.example.commission;

import org.example.model.Order;
import org.example.model.PaymentType;

public class TransferCommission implements CommissionStrategy {
    @Override
    public PaymentType supports() {
        return PaymentType.TRANSFER;
    }

    @Override
    public double calculate(Order order) {
        return order.getAmount() * 0.01;
    }
}
