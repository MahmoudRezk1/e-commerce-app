package com.bar.customer.dto;

import com.bar.customer.entity.Address;

public record CustomerResponse (
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
){
}
