package model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRow {

    private Long id;

    @NotNull
    private Long orderId;

    @NotNull
    private String itemName;

    @NotNull
    @Size(min = 1)
    private int quantity;

    @NotNull
    @Size(min = 1)
    private int price;

    //TODO: Delete constructor if not in use!
    // Use -> .setFieldName() instead.
    public OrderRow(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
