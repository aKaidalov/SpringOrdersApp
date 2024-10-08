package myapp;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

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
                throw new SQLException("Failed to insert new order");
            }

            return new Order(rs.getLong("id"),
                    order.getOrderNumber(),
                    null);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order getOrderById(long id) {

        String sql = "SELECT id, order_number FROM orderr WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Order(
                        rs.getLong("id"),
                        rs.getString("order_number"),
                        null);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Order> getAllOrders() {

        String sql = "SELECT id, order_number FROM orderr";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            List<Order> orders = new ArrayList<>();

            while (rs.next()) {

                Order order = new Order(
                        rs.getLong("id"),
                        rs.getString("order_number"),
                        null);

                orders.add(order);
            }

            return orders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
