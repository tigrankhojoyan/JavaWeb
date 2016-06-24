package com.vquick.rest.service.config;

import com.vquick.business.config.BusinessConfig;
import com.vquick.business.service.UserManagerService;
import com.vquick.business.service.impl.UserManagerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by tigran on 1/31/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.vquick.rest.service")
@Import(BusinessConfig.class)
public class RestConfig {

    @Bean
    public UserManagerService userDaoService() {
        return new UserManagerServiceImpl();
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

}
