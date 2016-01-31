package com.vquick.rest.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by tigran on 1/31/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.vquick.rest.service")
public class RestConfig {

}
