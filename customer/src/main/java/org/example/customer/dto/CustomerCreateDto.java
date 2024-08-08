package org.example.customer.dto;

public record CustomerCreateDto (
        String firstName,
        String lastName,
        String email
){
}
