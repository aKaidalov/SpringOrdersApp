package myapp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/api/orders")
public class OrdersServlet extends HttpServlet {

    private long orderId = 1;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String input = req.getReader().readLine();
        Order order = new ObjectMapper().readValue(input, Order.class);

        if (order.getId() == null) {
            order.setId(generateId());
        }

        getServletContext().setAttribute(order.getId().toString(), order);

        createResponse(resp, order);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idFromReq = req.getParameter("id");

        Order order = (Order) getServletContext().getAttribute(idFromReq);

        createResponse(resp, order);
    }

    private void createResponse(HttpServletResponse resp, Order order) throws IOException {
        String output = new ObjectMapper().writeValueAsString(order);
        resp.setContentType("application/json");
        resp.getWriter().write(output);
    }

    private synchronized long generateId() {
        return orderId++;
    }
}