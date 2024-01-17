package com.blog.BlogBackend.services.abstracts;

import com.blog.BlogBackend.dto.request.CategorySaveRequest;
import com.blog.BlogBackend.dto.request.CategoryUpdateRequest;
import com.blog.BlogBackend.dto.response.CategoryResponse;
import com.blog.BlogBackend.entities.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();
    Category getCategoryByID(long id);
    CategoryResponse saveCategory(CategorySaveRequest categoryRequest);
    CategoryResponse updateCategory(CategoryUpdateRequest categoryUpdateRequest);
    CategoryResponse deleteCategory(long id);
}
