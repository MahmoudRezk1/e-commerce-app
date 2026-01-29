package com.bar.order.controller;

import com.bar.order.dto.orderline.OrderLineResponse;
import com.bar.order.service.orderline.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-lines")
@RequiredArgsConstructor
public class OrderLineController {
    private final OrderLineService service;

    @GetMapping(path = "/order/{order-id}")
    public ResponseEntity<List<OrderLineResponse>> findByOrderId(@PathVariable(name = "order-id") Integer orderId) {
        return ResponseEntity.ok(service.findAllByOrderId(orderId));
    }

}
