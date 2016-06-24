package com.vquick.dao.config;

import com.vquick.dao.exception.DaoException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by tigran on 6/4/16.
 */
@Configuration
@Import(DaoConfig.class)
public class DaoConfigTest extends DaoConfig {

    @Bean
    public DataSource dataSource() throws DaoException {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:howtodoinjava");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    protected Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.archive.autodetection", "class,hbm");
        properties.put("hibernate.show_sql", "true");
        return properties;
    }
}