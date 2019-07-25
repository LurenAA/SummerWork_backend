package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.CacheControl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import project.dao.DaoConfig;
import project.service.ServiceConfig;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@Import({ServiceConfig.class})
@ComponentScan("project.web")
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    public HandlerInterceptor aAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(aAuthenticationInterceptor)
                .addPathPatterns("/request/passwd");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/dist/index.html");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/dist/static/");
    }
}
