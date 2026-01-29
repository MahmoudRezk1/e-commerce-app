package com.bar.order.clients;

import com.bar.order.dto.product.PurchaseRequest;
import com.bar.order.dto.product.PurchaseResponse;
import com.bar.order.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<List<PurchaseRequest>> entity = new HttpEntity<>(requestBody, headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {
                };
        ResponseEntity<List<PurchaseResponse>> response = restTemplate.exchange(
                productUrl + "/purchase",
                POST,
                entity,
                responseType
        );
        if (response.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the product purchase request: " + response.getStatusCode());
        }
        return response.getBody();
    }
}
