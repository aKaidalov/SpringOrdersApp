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
    }
}