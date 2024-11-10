package main;

import config.DbConfig;
import config.HsqlDataSource;
import myapp.OrderDao;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
              new AnnotationConfigApplicationContext(
                      DbConfig.class, HsqlDataSource.class);

        OrderDao dao = ctx.getBean(OrderDao.class);

    }
}