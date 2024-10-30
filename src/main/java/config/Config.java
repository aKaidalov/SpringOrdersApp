package config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"myapp", "config"})
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