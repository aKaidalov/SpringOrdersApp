package myapp;

import jakarta.validation.Valid;
import model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderDao orderDao;

    public OrderController(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @GetMapping("orders/{id}")
    public Order getOrderById(@PathVariable("id") Long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @GetMapping("orders")
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @PostMapping("orders")
    @ResponseStatus(HttpStatus.OK)
    public Order saveOrder(@RequestBody @Valid Order order) {
        return orderDao.saveOrder(order);
    }

    @DeleteMapping("orders/{id}")
    public void deleteOrderById(@PathVariable("id") Long orderId) {
        orderDao.deleteOrderById(orderId);
    }
}
