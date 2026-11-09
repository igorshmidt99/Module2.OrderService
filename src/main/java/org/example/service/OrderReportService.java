package org.example.service;

import org.example.model.Order;

import java.io.PrintStream;

public class OrderReportService {

    private final PrintStream stream;

    public OrderReportService(PrintStream stream) {
        this.stream = stream;
    }

    public void generateReport(Order order, double discount, double commission, double finalAmount) {
        stream.println("=== RECEIPT ===");
        stream.println("Order ID: " + order.getId());
        stream.println("Items: " + order.getItems());
        stream.println("Base amount: " + order.getAmount());
        stream.println("Discount: -" + discount);
        stream.println("Commission: +" + commission);
        stream.println("Final amount: " + finalAmount);
        stream.println("================");
    }

}
