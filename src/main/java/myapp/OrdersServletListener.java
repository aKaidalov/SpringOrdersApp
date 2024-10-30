//package myapp;
//
//import config.Config;
//import config.HsqlDataSource;
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//
//@WebListener
//public class OrdersServletListener implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//
//        var ctx = new AnnotationConfigApplicationContext(
//                Config.class,
//                HsqlDataSource.class);
//
//        try (ctx) {
//
//            sce.getServletContext().setAttribute("orderDao",
//                    ctx.getBean(OrderDao.class));
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}
