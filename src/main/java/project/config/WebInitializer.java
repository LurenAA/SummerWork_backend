package project.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import project.web.WebConfig;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 *  DispatcherServlet
 */
public class WebInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // Optionally also set maxFileSize, maxRequestSize, fileSizeThreshold
        registration.setMultipartConfig(new MultipartConfigElement("C://temp", 5242880,20971520,0));
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
