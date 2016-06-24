package com.vquick.business.config;

import com.vquick.business.manager.UserManager;
import com.vquick.business.manager.impl.UserManagerImpl;
import com.vquick.dao.config.DaoConfig;
import com.vquick.dao.persist.UserDaoService;
import com.vquick.dao.persist.impl.UserDaoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by tigran on 4/25/16.
 */

@Configuration
@ComponentScan(basePackages = "com.vquick.business")
@Import(DaoConfig.class)
public class BusinessConfig {

    @Bean
    public UserDaoService userDaoService() {
        return new UserDaoServiceImpl();
    }

    @Bean
    public UserManager userManager() {
        return new UserManagerImpl();
    }

}
