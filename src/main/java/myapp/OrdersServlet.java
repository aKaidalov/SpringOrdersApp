package myapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import tests.model.ValidationError;
import tests.model.ValidationErrors;


@WebServlet("/api/orders")
public class OrdersServlet extends HttpServlet {

    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = (OrderDao) getServletContext().getAttribute("orderDao");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        String input = request.getReader().readLine();
        Order order = new ObjectMapper().readValue(input, Order.class);

        if (order.getOrderNumber().length() < 2) {
            ValidationError error = new ValidationError("400");
            error.addArgument("too_short_number");

            ValidationErrors errors = new ValidationErrors();
            errors.addError(error);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            new ObjectMapper().writeValue(response.getOutputStream(), errors);
            return;
        }

        Order insertedOrder = orderDao.insertOrder(order);

        new ObjectMapper().writeValue(response.getOutputStream(), insertedOrder);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        String orderId = request.getParameter("id");

        if (orderId != null) {

            long id = Long.parseLong(orderId);
            Order order = orderDao.getOrderById(id);

            if (order != null) {
                new ObjectMapper().writeValue(response.getOutputStream(), order);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {

            List<Order> orders = orderDao.getAllOrders();
            new ObjectMapper().writeValue(response.getOutputStream(), orders);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("id");

        if (orderId != null) {
            long id = Long.parseLong(orderId);
            orderDao.deleteOrder(id);
        }
    }
}