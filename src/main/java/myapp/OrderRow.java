package myapp;

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
    private int quantity;
    private int price;

    OrderRow(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
