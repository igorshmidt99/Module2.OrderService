package org.example.service;

import org.example.commission.CardCommission;
import org.example.commission.CashCommission;
import org.example.commission.CommissionCalculator;
import org.example.commission.TransferCommission;
import org.example.discount.DiscountCalculator;
import org.example.discount.PremiumDiscount;
import org.example.discount.RegularDiscount;
import org.example.discount.VipDiscount;
import org.example.model.CustomerType;
import org.example.model.Order;
import org.example.model.PaymentType;
import org.example.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

/**
 Контекст: Тебе достался в наследство код от предыдущего разработчика — сервис обработки заказов интернет-магазина OrderService.
 Он работает, тесты (если бы они были) прошли бы, заказчик доволен... пока не попросил добавить новый тип клиента или способ оплаты.
 Тут выясняется, что каждое такое "простое" изменение требует лезть в один и тот же метод на 80 строк, где всё завязано друг на друга,
 и есть риск что-то сломать в соседней логике.
 Твоя задача: не переписать сервис с нуля, а отрефакторить — сохранив то же поведение (те же скидки, та же комиссия, тот же чек)
 Опирайся на SOLID и паттерны проектирования
Lombok использовать нельзя, все писать руками.
 */
public class OrderService {

    private final OrderRepository repository;
    private final OrderValidator validator;
    private final DiscountCalculator discountCalculator;
    private final CommissionCalculator commissionCalculator;
    private final OrderReportService reportService;

    public OrderService(OrderRepository repository, OrderValidator validator, DiscountCalculator discountCalculator,
                        CommissionCalculator commissionCalculator, OrderReportService reportService) {
        this.repository = repository;
        this.validator = validator;
        this.discountCalculator = discountCalculator;
        this.commissionCalculator = commissionCalculator;
        this.reportService = reportService;
    }

    public double processOrder(Order order) {

        // --- валидация ---
        validator.validate(order);

        // --- расчёт скидки по типу клиента ---
        double discount = discountCalculator.calculateDiscount(order);

        // --- расчёт комиссии по способу оплаты ---
        double commission = commissionCalculator.calculate(order);

        // --- итоговая сумма ---
        double finalAmount = order.getAmount() - discount + commission;

        // --- формирование чека прямо тут ---
        reportService.generateReport(order, discount, commission, finalAmount);

        // --- "сохранение" заказа  ---
        repository.add(order);

        return finalAmount;
    }

    public static void main(String[] args) {
        OrderRepository orderRepository = new OrderRepository(new ArrayList<>());
        OrderValidator orderValidator = new OrderValidator();
        DiscountCalculator discountCalculator = new DiscountCalculator(
                List.of(new VipDiscount(), new RegularDiscount(), new PremiumDiscount()));
        CommissionCalculator commissionCalculator = new CommissionCalculator(
                List.of(new CardCommission(), new CashCommission(), new TransferCommission()));
        OrderReportService orderReportService = new OrderReportService(System.out);

        OrderService service = new OrderService(orderRepository, orderValidator,
                discountCalculator, commissionCalculator, orderReportService);

        Order order1 = new Order("1", CustomerType.VIP, PaymentType.CARD, List.of("Laptop", "Mouse"), 1000.0);
        Order order2 = new Order("2", CustomerType.REGULAR, PaymentType.CASH, List.of("Book"), 20.0);

        System.out.println("Final amount 1: " + service.processOrder(order1));
        System.out.println("Final amount 2: " + service.processOrder(order2));
    }
}

/*
* Был нарушен принцип Single Responsibility - он гласит, что причина для изменения у класса должна быть одна.
* В то время как класс содержал логику по: сохранению заказа, расчета скидки, расчета комиссии, генерации чека и валидации
* Решение: выделить отдельные классы для каждой бизнес-задачи
*
* Был нарушен принцип Open/Closed - для того, чтобы добавить логику по вычислению комиссии и скидки
* необходимо было менять существующий код
* Решение: применение паттерна стратегия для каждого типа комиссии и скидки
*
* Был нарушен принцип Dependency Inversion - у класса OrderService инициализация контейнера List была через поле
* Решение: вынести инициализацию в конструтор, чтобы исключить зависимость класса от реализации и сделать его зависимым от абстракции
* */

