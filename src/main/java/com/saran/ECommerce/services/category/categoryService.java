package com.saran.ECommerce.services.category;

import com.saran.ECommerce.Exceptions.AlreadyExistsException;
import com.saran.ECommerce.Exceptions.ResourceNotFoundException;
import com.saran.ECommerce.Repository.CategoryRepository;
import com.saran.ECommerce.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class categoryService implements IcategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
//        if(categoryRepository.existsByName(category.getName())){
//            throw new AlreadyExistsException( category.getName()+" Category already Exists");
//        }
//        else{
//            return categoryRepository.save(category);
//        }

        return Optional.of(category).filter(c-> !categoryRepository.existsByName(category.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()-> new AlreadyExistsException( category.getName()+" Category already Exists"));
    }

    @Override
    public Category updateCategory( Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory->{
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if(categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        }
        else {
            throw new ResourceNotFoundException("Category Not Found..!");
        }
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found.."));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if(category == null) {
            throw new ResourceNotFoundException("Category Not Found..");
        }
        return category;
    }
}
