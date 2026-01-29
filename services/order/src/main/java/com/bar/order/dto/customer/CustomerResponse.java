package com.bar.order.dto.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
