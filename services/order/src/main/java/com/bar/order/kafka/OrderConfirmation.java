package com.bar.order.kafka;

import com.bar.order.dto.customer.CustomerResponse;
import com.bar.order.dto.product.PurchaseResponse;
import com.bar.order.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
