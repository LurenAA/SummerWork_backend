package project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.InputStream;
/**
 *   根配置
 */
@Configuration
@Import({SqlConfig.class})
@ComponentScan(basePackages = {"project"},
        excludeFilters = {
        @ComponentScan.Filter(value={EnableWebMvc.class})
        })
public class RootConfig {

}
