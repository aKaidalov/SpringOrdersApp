package model;

import jakarta.persistence.*;
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
//@With
@Entity
public class Order extends BaseEntity {

    @NotNull
    private String orderNumber;

    @Valid
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_rows",
            joinColumns = @JoinColumn(name = "orders_id",
                                        referencedColumnName = "id"))
    private List<OrderRow> orderRows;

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void addOrderRow(OrderRow orderRow) {
        this.orderRows.add(orderRow);
    }
}