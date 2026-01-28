package com.bar.product.dto;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
