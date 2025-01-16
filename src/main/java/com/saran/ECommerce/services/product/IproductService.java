package com.saran.ECommerce.services.product;

import com.saran.ECommerce.Request.AddProductRequest;
import com.saran.ECommerce.Request.UpdateProductRequest;
import com.saran.ECommerce.models.Product;

import java.util.List;

public interface IproductService {

    Product addProduct(AddProductRequest product);
    Product updateProduct(Long id, UpdateProductRequest product);
    void deleteProduct(Long id);
    Product getProductById(Long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String productName);
    List<Product> getProductsByBrandAndName(String brand, String productName);
    Long countProductsByBrandAndName(String brand, String productName);
}
