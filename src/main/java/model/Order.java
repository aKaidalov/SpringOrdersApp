package model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Order {

    private Long id;

    @NotNull
    private String orderNumber;

    @Valid
    @NotNull
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