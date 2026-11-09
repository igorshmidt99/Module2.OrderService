package org.example.commission;

import org.example.model.Order;
import org.example.model.PaymentType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommissionCalculator {

    private final Map<PaymentType, CommissionStrategy> strategies;

    public CommissionCalculator(List<CommissionStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(CommissionStrategy::supports, Function.identity()));
    }

    public double calculate(Order order) {
        return strategies.get(order.getPaymentType()).calculate(order);
    }
}
