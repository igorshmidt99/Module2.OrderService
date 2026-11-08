package org.example.discount;

import org.example.model.CustomerType;
import org.example.model.Order;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscountCalculator {

    private final Map<CustomerType, DiscountsStrategy> strategies;

    public DiscountCalculator(List<DiscountsStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(DiscountsStrategy::supports, Function.identity()));;
    }

    public double calculateDiscount(Order order) {
        return strategies.get(order.getCustomerType()).calculate(order);
    }
}
