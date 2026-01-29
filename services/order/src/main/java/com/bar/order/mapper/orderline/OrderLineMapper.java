package com.bar.order.mapper.orderline;

import com.bar.order.dto.orderline.OrderLineRequest;
import com.bar.order.dto.orderline.OrderLineResponse;
import com.bar.order.entity.Order;
import com.bar.order.entity.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(
                        Order.builder()
                                .id(request.id())
                                .build()
                )
                .productId(request.productId())
                .build();
    }

    public OrderLineResponse fromOrderLine(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
