package com.vquick.dao.config;

import com.vquick.dao.exception.DaoException;
import com.vquick.dao.util.ConfigReader;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by tigran on 4/27/16.
 */

@Configuration
@ComponentScan(basePackages = "com.vquick.dao")
public class DaoConfig {

    private static final String DB_CONFIG_FILE_NAME = "dbData.properties";
    private static ConfigReader configReader = new ConfigReader(DB_CONFIG_FILE_NAME);

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws DaoException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.vquick.dao.data" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() throws DaoException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(configReader.getProperties().getProperty("db.driver.class"));
        dataSource.setUrl("jdbc:mysql://localhost:3306/vquick");
        dataSource.setUsername("root");
        dataSource.setPassword("zaqxsw12");

        return dataSource;
    }

    protected Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

}
