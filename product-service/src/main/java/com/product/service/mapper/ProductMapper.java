package com.product.service.mapper;

import com.product.service.domain.Product;
import com.product.service.dto.ProductResponse;

public class ProductMapper {

    public static ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getPrice(),
                product.getStock()
        );
    }
}
