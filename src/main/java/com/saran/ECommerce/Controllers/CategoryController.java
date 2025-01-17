package com.saran.ECommerce.Controllers;

import com.saran.ECommerce.Exceptions.AlreadyExistsException;
import com.saran.ECommerce.Exceptions.ResourceNotFoundException;
import com.saran.ECommerce.Responses.ApiResponse;
import com.saran.ECommerce.models.Category;
import com.saran.ECommerce.services.category.IcategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final IcategoryService categoryService;

    public CategoryController(IcategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse> uploadCategory(@RequestBody Category category) {
        try {
            Category category1 = categoryService.addCategory(category);
            return new ResponseEntity<>(new ApiResponse("Upload success",category1), HttpStatus.OK);
        }
        catch(AlreadyExistsException e) {
            return new ResponseEntity<>(new ApiResponse("Upload failed",e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            Category category1 = categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(new ApiResponse("Update success",category1), HttpStatus.OK);
        }
        catch(ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("Update failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(new ApiResponse("Delete success",null), HttpStatus.OK);
        }
        catch(ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("Delete failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{categoryId}")
    public ResponseEntity<ApiResponse> getCategory(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return new ResponseEntity<>(new ApiResponse("Get success",category), HttpStatus.OK);
        }
        catch(ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("Get failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-category")
    public ResponseEntity<ApiResponse> getAllCategory() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(new ApiResponse("Get success",categories), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new ApiResponse("Get failed",e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryName(@PathVariable String categoryName) {
        try{
           Category category = categoryService.getCategoryByName(categoryName);
           return new ResponseEntity<>(new ApiResponse("Get success",category), HttpStatus.OK);
        }
        catch(ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("Get failed",e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
