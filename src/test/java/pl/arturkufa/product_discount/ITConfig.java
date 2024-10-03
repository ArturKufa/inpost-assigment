package pl.arturkufa.product_discount;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = { "pl.arturkufa" })
@EnableWebMvc
public class ITConfig {
}
