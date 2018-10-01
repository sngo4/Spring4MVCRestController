package spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
 * @Component indicates that a class might be a candidate for creating a bean. Its like putting a hand up.
 * @ComponentScan is searching packages for Components. Trying to find out who all put their hands up.
 * 
 * We are using @EnableWebMvc, @ComponentScan and @Configuration annotations.
 * These will bootstrap the spring mvc application and set package to scan controllers and resources.
 * 
 * */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "spring")
public class AppConfig {

}
