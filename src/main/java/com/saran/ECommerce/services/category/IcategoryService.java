package com.saran.ECommerce.services.category;

import com.saran.ECommerce.models.Category;

import java.util.List;

public interface IcategoryService {

    Category addCategory(Category category);
    Category updateCategory(Long id , Category category);
    void deleteCategory(Long categoryId);
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategories();
    Category getCategoryByName(String categoryName);
}
