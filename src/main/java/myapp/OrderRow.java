package myapp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRow {
    private String itemName;
    private int quantity;
    private int price;

}
