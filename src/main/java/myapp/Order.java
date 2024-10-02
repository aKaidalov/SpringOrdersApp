package myapp;

public class Order {
    private Long id;
    private String orderNumber;
    private OrderRow[] orderRows;


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

    public OrderRow[] getOrderRows() {
        return orderRows;
    }

    public void setOrderRows(OrderRow[] orderRows) {
        this.orderRows = orderRows;
    }
}