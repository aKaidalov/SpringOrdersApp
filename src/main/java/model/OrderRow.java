package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderRow {

    private String itemName;

    @NotNull
    @Min(1)
    private int price;

    @NotNull
    @Min(1)
    private int quantity;

    @Column(name = "orders_id")
    private Long orderId;

    //used in Kalmo's tests
    public OrderRow(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
