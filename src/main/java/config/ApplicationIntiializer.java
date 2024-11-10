package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationIntiializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/*" };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { Config.class };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }
}