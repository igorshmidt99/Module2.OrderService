package org.example.commission;

import org.example.model.Order;
import org.example.model.PaymentType;

public interface CommissionStrategy {
    PaymentType supports();
    double calculate(Order order);
}
