package com.bar.customer.service;

import com.bar.customer.dto.CustomerRequest;
import com.bar.customer.dto.CustomerResponse;
import com.bar.customer.entity.Customer;
import com.bar.customer.exception.CustomerNotFoundException;
import com.bar.customer.mapper.CustomerMapper;
import com.bar.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepository.findById(customerRequest.id()).orElseThrow(
                () -> new CustomerNotFoundException(
                        format(
                                "Cannot update customer:: No customer found with the provided id:: %s",
                                customerRequest.id()
                        )
                )
        );
        mergeCustomer(customer, customerRequest);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.firstName())) {
            customer.setFirstName(customerRequest.firstName());
        }
        if (StringUtils.isNotBlank(customerRequest.lastName())) {
            customer.setLastName(customerRequest.lastName());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository
                .findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(
                        () -> new CustomerNotFoundException(format("Customer not found with the provided customerId: %s" , customerId))
                );
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
