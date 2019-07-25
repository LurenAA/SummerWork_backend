package project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * sql操作需要的DataSource和NamedParameterJdbcOperations配置，
 * 导入到根配置中
 */
public class SqlConfig {
    @Bean
    public DataSource dataSource() throws ClassNotFoundException, IOException {
        InputStream inputStream = SqlConfig.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        Class.forName("com.mysql.jdbc.Driver");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(properties.getProperty("url"));//"jdbc:mysql://localhost:3306/lab?serverTimezone=UTC");
        dataSource.setUsername(properties.getProperty("username"));//"root");
        dataSource.setPassword(properties.getProperty("passwd"));
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcOperations namedParameterJdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
