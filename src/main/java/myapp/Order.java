package myapp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNumber;
    private List<OrderRow> orderRows;

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void addOrderRow(OrderRow orderRow) {
        if (orderRows == null) {
            orderRows = new ArrayList<>();
        }
        this.orderRows.add(orderRow);
    }
}