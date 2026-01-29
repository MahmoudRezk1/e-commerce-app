package com.bar.payment.service;

import com.bar.payment.dto.PaymentRequest;
import com.bar.payment.kafka.NotificationProducer;
import com.bar.payment.kafka.PaymentNotificationRequest;
import com.bar.payment.mapper.PaymentMapper;
import com.bar.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final NotificationProducer notificationProducer;
    private final PaymentMapper mapper;


    public Integer createPayment(PaymentRequest request) {
        var payment = paymentRepository.save(mapper.toPayment(request));
        this.notificationProducer.sendNotification(new PaymentNotificationRequest(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstName(),
                request.customer().lastName(),
                request.customer().email()
        ));
        return payment.getId();
    }
}
