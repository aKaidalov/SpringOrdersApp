package myapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/api/orders")
public class OrdersServlet extends HttpServlet {

    private long orderId = 1;
    private OrderMapper orderMapper = new OrderMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String input = req.getReader().readLine();

        Order order = orderMapper.parse(input);

        if (order.getId() == null) {
            order.setId(generateId());
        }

        String output = orderMapper.stringify(order);

        resp.setContentType("application/json");

        resp.getWriter().write(output);
    }

    private long generateId() {
        return orderId++;
    }
}