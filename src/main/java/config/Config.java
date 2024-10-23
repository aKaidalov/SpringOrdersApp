package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;


@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"myapp"})
public class Config {

    @Bean
    private JdbcClient jdbcClient(DataSource dataSource) {

        // Create db table and insert rows when init
        var populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"));

        DatabasePopulatorUtils.execute(populator, dataSource);

        return JdbcClient.create(dataSource);
    }
}