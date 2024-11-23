package config;

import config.security.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationIntiializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { MvcConfig.class,
                             SecurityConfig.class,
                             HsqlDataSource.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { Config.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/*" };
    }
}