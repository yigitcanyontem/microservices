package org.example.customer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.dto.CustomerCreateDto;
import org.example.customer.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public void registerCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        log.info("Registering customer: {}", customerCreateDto);
        customerService.saveCustomer(customerCreateDto);
    }
}
