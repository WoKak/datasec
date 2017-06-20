package datasec.config;

import datasec.domain.LoggedUser;
import datasec.domain.repository.CodeRepository;
import datasec.domain.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;

/**
 * Created by Michał on 2017-04-01.
 */

@Configuration
@PropertySource(value = {"classpath:database/database.properties"})
public class AppConfig {


    @Autowired
    private Environment env;

    /**
     * Database bean
     */
    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getRequiredProperty("jdbc.drivers"));
        ds.setUrl(env.getRequiredProperty("jdbc.url"));
        ds.setUsername(env.getRequiredProperty("jdbc.username"));
        ds.setPassword(env.getRequiredProperty("jdbc.password"));

        return ds;
    }

    /**
     * Validator bean
     */
    @Bean
    public LocalValidatorFactoryBean validator() {

        LocalValidatorFactoryBean v = new LocalValidatorFactoryBean();
        return v;
    }

    /**
     * Code repo bean
     */
    @Bean
    public CodeRepository codeRepository() {

        CodeRepository cr = new CodeRepository();
        CodeService cs = new CodeService(cr, dataSource());
        cs.initRepo();
        return cr;
    }

    /**
     * Logged user bean
     */
    @Bean
    public LoggedUser loggedUser() {

        LoggedUser lg = new LoggedUser("");
        return lg;
    }
}
