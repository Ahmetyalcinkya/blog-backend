package com.blog.BlogBackend.controllers;

import com.blog.BlogBackend.dto.request.CategorySaveRequest;
import com.blog.BlogBackend.dto.request.CategoryUpdateRequest;
import com.blog.BlogBackend.dto.response.CategoryResponse;
import com.blog.BlogBackend.entities.Category;
import com.blog.BlogBackend.services.abstracts.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/admin/saveCategory")
    public CategoryResponse saveCategory(@RequestBody CategorySaveRequest categorySaveRequest){
        return categoryService.saveCategory(categorySaveRequest);
    }
    @PutMapping("/admin/updateCategory")
    public CategoryResponse updateCategory(@RequestBody CategoryUpdateRequest categoryUpdateRequest){
        return categoryService.updateCategory(categoryUpdateRequest);
    }
    @DeleteMapping("/admin/deleteCategory/{id}")
    public CategoryResponse deleteCategory(@PathVariable long id){
        return categoryService.deleteCategory(id);
    }
}
