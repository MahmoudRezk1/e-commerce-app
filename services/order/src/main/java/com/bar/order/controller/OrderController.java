package com.bar.order.controller;

import com.bar.order.dto.order.OrderRequest;
import com.bar.order.dto.order.OrderResponse;
import com.bar.order.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(service.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/{order_id}")
    public ResponseEntity<OrderResponse> findOrderByIf(@PathVariable(name = "order_id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
