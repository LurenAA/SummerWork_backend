package project.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import project.dao.DaoConfig;

/**
 * service层config
 */
@Configuration
@ComponentScan
@Import({DaoConfig.class})
public class ServiceConfig {

}
