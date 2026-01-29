package com.bar.order.service.orderline;

import com.bar.order.dto.orderline.OrderLineRequest;
import com.bar.order.dto.orderline.OrderLineResponse;
import com.bar.order.mapper.orderline.OrderLineMapper;
import com.bar.order.repository.orderline.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        var orderLine = mapper.toOrderLine(request);
        return repository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository
                .findAllByOrderId(orderId).stream()
                .map(mapper::fromOrderLine)
                .toList();
    }
}
