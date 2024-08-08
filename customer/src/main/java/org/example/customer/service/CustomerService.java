package org.example.customer.service;

import lombok.RequiredArgsConstructor;
import org.example.amqp.RabbitMQMessageProducer;
import org.example.clients.fraud.FraudCheckResponse;
import org.example.clients.fraud.FraudClient;
import org.example.clients.notification.NotificationClient;
import org.example.clients.notification.NotificationCreateDto;
import org.example.customer.dto.CustomerCreateDto;
import org.example.customer.entity.Customer;
import org.example.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void saveCustomer(CustomerCreateDto customerCreateDto) {
        Customer customer = Customer.builder()
                .firstName(customerCreateDto.firstName())
                .lastName(customerCreateDto.lastName())
                .email(customerCreateDto.email())
                .build();
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()){
            throw new IllegalStateException("Email taken");
        }
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if (fraudCheckResponse != null && fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("Fraudster");
        }

        NotificationCreateDto notificationCreateDto = new NotificationCreateDto(
                customer.getId(),
                "Welcome to our platform!"
        );
        rabbitMQMessageProducer
                .publish(notificationCreateDto,
                        "internal.exchange",
                        "internal.notifications.routing-key");

    }
}
