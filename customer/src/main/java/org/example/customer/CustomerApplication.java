package org.example.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "org.example.customer",
                "org.example.amqp"
        }

)
@EnableEurekaClient
@EnableFeignClients(
        basePackageClasses = {
                org.example.clients.fraud.FraudClient.class,
                org.example.clients.notification.NotificationClient.class}
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
