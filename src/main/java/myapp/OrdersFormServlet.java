package myapp;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@WebServlet("/orders/form")
public class OrdersFormServlet extends HttpServlet {

    private long orderId = 1;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderNumber = req.getParameter("orderNumber");

        Order order = new Order();
        order.setId(generateId());
        order.setOrderNumber(orderNumber);

        ServletContext context = getServletContext();
        context.setAttribute(String.valueOf(order.getId()), order);

        String headerAccept = req.getHeader("Accept");

        if (headerAccept != null && headerAccept.contains("application/json")) {
            String output = objectMapper.writeValueAsString(order);
            resp.setContentType("application/json");
            resp.getWriter().write(output);
        } else {
            resp.setContentType("application/x-www-form-urlencoded");
            resp.getWriter().write("id=" + order.getId() + "&orderNumber=" + order.getOrderNumber());
        }

    }

    private synchronized long generateId() {
        return orderId++;
    }

}
