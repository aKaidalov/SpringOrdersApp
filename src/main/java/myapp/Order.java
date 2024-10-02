package myapp;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private String orderNumber;
    private List<OrderRow> orderRows = new ArrayList<>();

    public Order(String orderNumber) {
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