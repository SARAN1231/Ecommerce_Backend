package com.saran.ECommerce.Repository;

import com.saran.ECommerce.models.Category;
import com.saran.ECommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface productRepository extends JpaRepository<Product,Long> {

    List<Product> findByName(String name);
    List<Product> findByCategoryName(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryNameAndBrand(String category, String brand);
    List<Product> findByBrandAndName(String brand, String productName);

    Long countByBrandAndName(String brand, String productName);
}
