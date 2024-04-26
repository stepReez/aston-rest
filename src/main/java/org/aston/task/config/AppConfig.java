package org.aston.task.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.baeldung.autowire.sample")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
