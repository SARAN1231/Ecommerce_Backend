package com.saran.ECommerce.Controllers;

import com.saran.ECommerce.Exceptions.ProductNotFoundException;
import com.saran.ECommerce.Request.AddProductRequest;
import com.saran.ECommerce.Request.UpdateProductRequest;
import com.saran.ECommerce.Responses.ApiResponse;
import com.saran.ECommerce.models.Product;
import com.saran.ECommerce.services.product.IproductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final IproductService productService;

    public ProductController(IproductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> uploadProduct( @RequestBody AddProductRequest request) {
        try {
            Product product = productService.addProduct(request);
            return new ResponseEntity<>(new ApiResponse("upload success",product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("upload failed",e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct( @RequestBody UpdateProductRequest request, @PathVariable long productId) {
        try {
            Product product = productService.updateProduct(productId, request);
            return new ResponseEntity<>(new ApiResponse("update success",product), HttpStatus.OK);
        }
        catch (ProductNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("update failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable long productId) {
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(new ApiResponse("delete success",null), HttpStatus.OK);
        }
        catch (ProductNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("delete failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable long productId) {
        try {
            Product product = productService.getProductById(productId);
            return new ResponseEntity<>(new ApiResponse("get success",product), HttpStatus.OK);
        }
        catch (ProductNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("get failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping( "/all-products")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(new ApiResponse("get success", products), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("get failed", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@PathVariable String categoryName) {
        try{
           List<Product> products = productService.getProductsByCategory(categoryName);
            return new ResponseEntity<>(new ApiResponse("get success",products), HttpStatus.OK);
        }
        catch (ProductNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("get failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable  String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            return new ResponseEntity<>(new ApiResponse("get success",products), HttpStatus.OK);
        }
        catch (ProductNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("get failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/categoryName-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryNameAndBrand(@RequestParam String categoryName, @RequestParam String brandName) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            return new ResponseEntity<>(new ApiResponse("get success",products), HttpStatus.OK);
        }
        catch (ProductNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("get failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<ApiResponse> getProductByProductName(@PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            return new ResponseEntity<>(new ApiResponse("get success",products), HttpStatus.OK);
        }
        catch (ProductNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("get failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/brand-and-productName")
    public ResponseEntity<ApiResponse> getProductByBrandAndProductName(@RequestParam String brand, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, productName);
            return new ResponseEntity<>(new ApiResponse("get success",products), HttpStatus.OK);
        }
        catch (ProductNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("get failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count/brand-and-productName")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,@RequestParam String productName) {
        try {
            Long count = productService.countProductsByBrandAndName(brand, productName);

                return new ResponseEntity<>(new ApiResponse("product Count ", count),HttpStatus.OK);

        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("Error",e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
