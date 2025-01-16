package com.saran.ECommerce.Mapper;

import com.saran.ECommerce.Request.AddProductRequest;
import com.saran.ECommerce.models.Category;
import com.saran.ECommerce.models.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(AddProductRequest productRequest, Category category) {
        return new Product(
                productRequest.getName(),
                productRequest.getBrand(),
                productRequest.getPrice(),
                productRequest.getDescription(),
                productRequest.getInventory(),
                category
        );
    }
}
