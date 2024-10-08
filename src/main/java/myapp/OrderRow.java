package myapp;

import lombok.Data;

@Data
public class OrderRow {
    private String itemName;
    private int quantity;
    private int price;

}
