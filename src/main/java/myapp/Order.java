package myapp;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long id;
    private String orderNumber;
    private List<OrderRow> orderRows = new ArrayList<>(); // Используем список для хранения строк заказа

    public Order() {}

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderRow> getOrderRows() {
        return new ArrayList<>(orderRows);
    }

    public void setOrderRows(List<OrderRow> orderRows) {
        this.orderRows = new ArrayList<>(orderRows);
    }

    public void addOrderRow(OrderRow orderRow) {
        this.orderRows.add(orderRow);
    }
}