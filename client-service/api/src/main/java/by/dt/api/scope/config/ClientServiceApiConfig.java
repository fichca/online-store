package by.dt.api.scope.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"by.dt.api.scope"})
@ComponentScan({"by.dt.api.scope"})
public class ClientServiceApiConfig {
}
