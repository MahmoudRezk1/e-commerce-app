package com.bar.customer.mapper;

import com.bar.customer.dto.CustomerRequest;
import com.bar.customer.entity.Customer;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(@Valid CustomerRequest customerRequest) {
        if (customerRequest == null) return null;
        return Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .address(customerRequest.address())
                .build();
    }
}
