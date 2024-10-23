package myapp;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class OrderDao {

    private static final String ORDER_ID_COLUMN = "order_id";
    private static final String ROW_ID_COLUMN = "row_id";

    private DataSource dataSource;

    public OrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Order insertOrder(Order order) {

        String sql = "INSERT INTO orderr (order_number) VALUES (?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[] {"id"})) {

            ps.setString(1, order.getOrderNumber());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (!rs.next()) {
                throw new SQLException("Failed to insert new ORDER");
            }

            Order updatedOrder = new Order(rs.getLong("id"),
                    order.getOrderNumber(),
                    new ArrayList<>());

            // Insert Order Rows
            if (order.getOrderRows() != null) {

                for (OrderRow orderRow : order.getOrderRows()) {

                    OrderRow updatedOrderRow = insertOrderRow(orderRow, updatedOrder);
                    updatedOrder.addOrderRow(updatedOrderRow);

                }
            }

            return updatedOrder;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private OrderRow insertOrderRow(OrderRow orderRow, Order order) {

        String sql = "INSERT INTO order_row (order_id, item_name, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[] {"id"})) {

            ps.setLong(1,order.getId());
            ps.setString(2, orderRow.getItemName());
            ps.setInt(3, orderRow.getQuantity());
            ps.setInt(4, orderRow.getPrice());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (!rs.next()) {
                throw new SQLException("Failed to insert new order ROW");
            }

            OrderRow updatedOrderRow = new OrderRow(orderRow.getItemName(),
                    orderRow.getQuantity(),
                    orderRow.getPrice());

            updatedOrderRow.setId(rs.getLong("id"));
            updatedOrderRow.setOrderId(order.getId());

            return updatedOrderRow;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Order getOrderById(long id) {

        String sql = "SELECT o.id AS order_id, o.order_number, r.id AS row_id, r.item_name, r.quantity, r.price " +
                "FROM orderr o " +
                "LEFT JOIN order_row r ON o.id = r.order_id " +
                "WHERE o.id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Order selectedOrder = null;

            while (rs.next()) {

                if (selectedOrder == null) {
                    selectedOrder = new Order(
                            rs.getLong(ORDER_ID_COLUMN),
                            rs.getString("order_number"),
                            new ArrayList<>()
                    );
                }

                if (rs.getLong(ROW_ID_COLUMN) > 0) {
                    OrderRow orderRow = new OrderRow(
                            rs.getLong(ROW_ID_COLUMN),
                            rs.getLong(ORDER_ID_COLUMN),
                            rs.getString("item_name"),
                            rs.getInt("quantity"),
                            rs.getInt("price")
                    );
                    selectedOrder.addOrderRow(orderRow);
                }
            }

            return selectedOrder;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Order> getAllOrders() {

        String sql = "SELECT o.id AS order_id, o.order_number, r.id AS row_id, r.item_name, r.quantity, r.price " +
                "FROM orderr o " +
                "LEFT JOIN order_row r ON o.id = r.order_id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<Order> orders = new ArrayList<>();
            Order currentOrder = null;

            while (rs.next()) {

                // firstIteration || isNewOrder
                if (currentOrder == null || rs.getLong(ORDER_ID_COLUMN) != currentOrder.getId()) {
                    // Add previous order if exists
                    if (currentOrder != null) {
                        orders.add(currentOrder);
                    }

                    currentOrder = new Order(rs.getLong(ORDER_ID_COLUMN),
                            rs.getString("order_number"),
                            new ArrayList<>());
                }

                if (rs.getLong(ROW_ID_COLUMN) > 0) {
                    OrderRow orderRow = new OrderRow(
                            rs.getLong(ROW_ID_COLUMN),
                            rs.getLong(ORDER_ID_COLUMN),
                            rs.getString("item_name"),
                            rs.getInt("quantity"),
                            rs.getInt("price")
                    );
                    currentOrder.addOrderRow(orderRow);
                }
            }

            // Add last order if exists
            if (currentOrder != null) {
                orders.add(currentOrder);
            }

            return orders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOrder(long id) {
        try (Connection conn = dataSource.getConnection()) {
            String sqlDeleteRows = "DELETE FROM order_row WHERE order_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteRows)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }

            String sqlDeleteOrder = "DELETE FROM orderr WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteOrder)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
