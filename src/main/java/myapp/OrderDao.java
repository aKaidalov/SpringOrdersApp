package myapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import model.Order;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Order saveOrder(Order order) {
        if (order.getId() == null) {
            em.persist(order);
            return order;
        } else {
            return em.merge(order);
        }
    }

    public Order getOrderById(long id) {
        return em.find(Order.class, id);
    }

    public List<Order> getAllOrders() {
        TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
        return query.getResultList();
    }

    @Transactional
    public void deleteOrderById(long id) {
        Order order = getOrderById(id);
        if (order != null) {
            em.remove(order);
        }
    }

}
