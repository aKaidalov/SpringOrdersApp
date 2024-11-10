package main;

import config.Config;
import config.HsqlDataSource;
import model.Order;
import model.OrderRow;
import myapp.OrderDao;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
              new AnnotationConfigApplicationContext(
                      Config.class, HsqlDataSource.class);

        OrderDao dao = ctx.getBean(OrderDao.class);

//        System.out.println(dao);
//
//        dao.saveOrder(new Order("A123"));
//        dao.saveOrder(new Order("B456"));
//        dao.saveOrder(new Order("C789", List.of(new OrderRow("PC", 1, 1), new OrderRow("AC", 2, 2))));
//
//        System.out.println(dao.getAllOrders());

    }
}