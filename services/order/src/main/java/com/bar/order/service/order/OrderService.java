package com.bar.order.service.order;

import com.bar.order.clients.CustomerClient;
import com.bar.order.clients.PaymentClient;
import com.bar.order.clients.ProductClient;
import com.bar.order.dto.order.OrderRequest;
import com.bar.order.dto.order.OrderResponse;
import com.bar.order.dto.orderline.OrderLineRequest;
import com.bar.order.dto.payment.PaymentRequest;
import com.bar.order.dto.product.PurchaseRequest;
import com.bar.order.exception.BusinessException;
import com.bar.order.kafka.OrderConfirmation;
import com.bar.order.kafka.OrderProducer;
import com.bar.order.mapper.order.OrderMapper;
import com.bar.order.repository.order.OrderRepository;
import com.bar.order.service.orderline.OrderLineService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final PaymentClient paymentClient;
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(@Valid OrderRequest request) {
        // check the customer --> OpenFeign
        var customer = this.customerClient
                .findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order. No customer exists with provided customerId :: " + request.customerId()));

        //purchase the product --> product-ms (RestTemplate)
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        //persist the order
        var order = this.repository.save(mapper.toOrder(request));

        //persist the order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            this.orderLineService.saveOrderLine(new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
            ));
        }
        // start the payment
        this.paymentClient.requestOrderPayment(new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                request.id(),
                request.reference(),
                customer
        ));
        //send the order confirmation --> notification-ms (Kafka)
        this.orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer id) {
        return repository.findById(id).map(mapper::fromOrder).orElseThrow(
                () -> new EntityNotFoundException("No order found with id :: " + id)
        );
    }
}
