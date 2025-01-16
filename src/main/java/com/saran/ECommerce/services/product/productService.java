package com.saran.ECommerce.services.product;

import com.saran.ECommerce.Exceptions.ProductNotFoundException;
import com.saran.ECommerce.Mapper.ProductMapper;
import com.saran.ECommerce.Repository.CategoryRepository;
import com.saran.ECommerce.Repository.productRepository;
import com.saran.ECommerce.Request.AddProductRequest;
import com.saran.ECommerce.Request.UpdateProductRequest;
import com.saran.ECommerce.models.Category;
import com.saran.ECommerce.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class productService implements IproductService{

    private final  productRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public productService(com.saran.ECommerce.Repository.productRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }


    @Override
    public Product addProduct(AddProductRequest productrequest) {
        Category category = Optional.ofNullable(categoryRepository.findByName(productrequest.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(productrequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        productrequest.setCategory(category);
        return productRepository.save(productMapper.toProduct(productrequest,category));
    }

    @Override
    public Product updateProduct(Long id, UpdateProductRequest product) {

        // old code not using map
//        Optional<Product> optionalProduct = productRepository.findById(id);
//        if (optionalProduct.isPresent()) {
//            Product existingProduct = optionalProduct.get();
//            Product updatedProduct = updateExistingproduct(existingProduct, product);
//            return productRepository.save(updatedProduct);
//        } else {
//            throw new ProductNotFoundException("Product Not Found");
//        }

        // advanced method to reduce the code
        return productRepository.findById(id) // returns optional<Product> //
                .map(existingProduct->updateExistingproduct(existingProduct,product)) // map is used to handle optional and map is work whew optional contains the value
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found")); // doest not contains value then this will be excueted

    }

    private Product updateExistingproduct(Product existingProduct, UpdateProductRequest productrequest ) {
        existingProduct.setName(productrequest.getName());
        existingProduct.setDescription(productrequest.getDescription());
        existingProduct.setPrice(productrequest.getPrice());
        existingProduct.setBrand(productrequest.getBrand());
        existingProduct.setInventory(productrequest.getInventory());

        Category category = categoryRepository.findByName(productrequest.getCategory().getName());
        existingProduct.setCategory(category);
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
        else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->
                new ProductNotFoundException("Product Not Found.....!"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        if(products.isEmpty()) {
            throw new ProductNotFoundException("Product Not Found");
        }
        return products;
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        List<Product> products =  productRepository.findByBrand(brand);
        if(products.isEmpty()) {
            throw new ProductNotFoundException("Product Not Found");
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products =  productRepository.findByCategoryNameAndBrand(category,brand);
        if(products.isEmpty()) {
            throw new ProductNotFoundException("Product Not Found");
        }
        return products;

    }

    @Override
    public List<Product> getProductsByName(String productName) {
        List<Product> products =  productRepository.findByName(productName);
        if(products.isEmpty()) {
            throw new ProductNotFoundException("Product Not Found");
        }
        return products;

    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String productName) {
        List<Product> products =  productRepository.findByBrandAndName(brand,productName);
        if(products.isEmpty()) {
            throw new ProductNotFoundException("Product Not Found");
        }
        return products;
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String productName) {
        return productRepository.countByBrandAndName(brand,productName);
    }
}
