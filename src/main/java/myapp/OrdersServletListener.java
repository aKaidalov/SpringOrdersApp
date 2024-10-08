package myapp;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import util.ConfigUtil;
import util.DevDataSource;
import util.FileUtil;

import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class OrdersServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {

            DevDataSource dataSource = new DevDataSource(ConfigUtil.readConnectionInfo());

            sce.getServletContext().setAttribute("dataSource", dataSource);

            String schema = FileUtil.readFileFromClasspath("schema.sql");

            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement()) {

                stmt.execute(schema);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
