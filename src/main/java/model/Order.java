package model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;

    @NotNull
    @Column(name = "order_number")
    private String orderNumber;

    @Valid
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_rows",
            joinColumns = @JoinColumn(name = "orders_id",
                                        referencedColumnName = "id"))
    private List<OrderRow> orderRows;

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Order(String orderNumber, List<OrderRow> orderRows) {
        this.orderNumber = orderNumber;
    }

    public void addOrderRow(OrderRow orderRow) {
        this.orderRows.add(orderRow);
    }
}