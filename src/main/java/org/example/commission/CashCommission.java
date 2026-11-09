package org.example.commission;

import org.example.model.Order;
import org.example.model.PaymentType;

public class CashCommission implements CommissionStrategy {

    @Override
    public PaymentType supports() {
        return PaymentType.CASH;
    }

    @Override
    public double calculate(Order order) {
        return 0.0;
    }
}
