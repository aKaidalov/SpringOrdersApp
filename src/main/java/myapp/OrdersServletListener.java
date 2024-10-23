package myapp;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.ComponentScan;
import util.ConfigUtil;
import util.DevDataSource;

@WebListener
public class OrdersServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {

            // Create data obj
            DevDataSource dataSource = new DevDataSource(ConfigUtil.readConnectionInfo());

            // Save data obj in ServletContex
            sce.getServletContext().setAttribute("dataSource", dataSource);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
