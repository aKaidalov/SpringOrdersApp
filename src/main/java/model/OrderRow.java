package model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRow {

    private Long id;

    private Long orderId;

    private String itemName;

    @NotNull
    @Min(1)
    private int quantity;

    @NotNull
    @Min(1)
    private int price;

    //used in Kalmo's tests
    public OrderRow(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
