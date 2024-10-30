package myapp;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class OrderDao {

    private static final String ORDER_ID_COLUMN = "order_id";
    private static final String ROW_ID_COLUMN = "row_id";

    private JdbcClient jdbcClient;

    public OrderDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Order insertOrder(Order order) {

        String sql = "INSERT INTO orderr (order_number) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .param(1, order.getOrderNumber())
                .update(keyHolder, "id");

        // Create a new Order obj from the updated copy of og order
        Order updatedOrder = order.withId(keyHolder.getKey().longValue());

        if (updatedOrder.getOrderRows() != null) {
            for (OrderRow orderRow : updatedOrder.getOrderRows()) {
                insertOrderRow(updatedOrder.getId(), orderRow);
            }
        }

        return updatedOrder;
    }

    private void insertOrderRow(long orderId, OrderRow orderRow) {

        String sql = "INSERT INTO order_row (order_id, item_name, quantity, price)" +
                " VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .param(1, orderId)
                .param(2, orderRow.getItemName())
                .param(3, orderRow.getQuantity())
                .param(4, orderRow.getPrice())
                .update(keyHolder, "id");

        Long generatedId = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;

        if (generatedId != null) {
            orderRow.setId(generatedId);
        } else {
            throw new RuntimeException("Failed to retrieve generated key for OrderRow");
        }
    }


    public Order getOrderById(long id) {

        String sql = "SELECT o.id AS order_id, o.order_number, r.id AS row_id, r.item_name, r.quantity, r.price " +
                "FROM orderr o " +
                "LEFT JOIN order_row r ON o.id = r.order_id " +
                "WHERE o.id = ?";

        return jdbcClient.sql(sql)
                .param(1, id)
                .query(rs -> {
                    Order order = null;
                    while (rs.next()) {
                        if (order == null) {
                            order = createNewOrderFromRs(rs);
                        }
                        if (rs.getLong(ROW_ID_COLUMN) > 0) {
                            OrderRow orderRow = createNewOrderRowFromRs(rs);
                            order.addOrderRow(orderRow);
                        }
                    }
                    return order;
                });

    }

    public List<Order> getAllOrders() {

        String sql = "SELECT o.id AS order_id, o.order_number, r.id AS row_id, r.item_name, r.quantity, r.price " +
                "FROM orderr o " +
                "LEFT JOIN order_row r ON o.id = r.order_id";

        return jdbcClient.sql(sql)
                .query(rs -> {
                    return getOrderList(rs);
                });
    }

    private List<Order> getOrderList(ResultSet rs) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Order currentOrder = null;

        while (rs.next()) {

            // firstIteration || isNewOrder
            if (currentOrder == null || rs.getLong(ORDER_ID_COLUMN) != currentOrder.getId()) {

                // Add previous order if exists
                if (currentOrder != null) {
                    orders.add(currentOrder);
                }
                currentOrder = createNewOrderFromRs(rs);
            }

            if (rs.getLong(ROW_ID_COLUMN) > 0) {
                OrderRow row = createNewOrderRowFromRs(rs);
                currentOrder.addOrderRow(row);
            }
        }

        // Add last order if exists
        if (currentOrder != null) {
            orders.add(currentOrder);
        }

        return orders;
    }

    public void deleteOrder(long id) {
        String sqlDeleteRows = "DELETE FROM order_row WHERE order_id = ?";
        jdbcClient.sql(sqlDeleteRows)
                .param(1, id)
                .update();


        String sqlDeleteOrder = "DELETE FROM orderr WHERE id = ?";
        jdbcClient.sql(sqlDeleteOrder)
                .param(1, id)
                .update();
    }


    private OrderRow createNewOrderRowFromRs(ResultSet rs) throws SQLException {
        return new OrderRow(
                rs.getLong(ROW_ID_COLUMN),
                rs.getLong(ORDER_ID_COLUMN),
                rs.getString("item_name"),
                rs.getInt("quantity"),
                rs.getInt("price")
        );
    }

    private Order createNewOrderFromRs(ResultSet rs) throws SQLException {
        return new Order(rs.getLong(ORDER_ID_COLUMN),
                rs.getString("order_number"),
                new ArrayList<>());
    }

}
