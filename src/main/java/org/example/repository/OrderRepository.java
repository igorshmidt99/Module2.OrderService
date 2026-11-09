package org.example.repository;

import org.example.model.Order;

import java.util.List;

public class OrderRepository {

    private final List<Order> orders;

    public OrderRepository(List<Order> orders) {
        this.orders = orders;
    }

    public void add(Order order) {
        orders.add(order);
    }

}
