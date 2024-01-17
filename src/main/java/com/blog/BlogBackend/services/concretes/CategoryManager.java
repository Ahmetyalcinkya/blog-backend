package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.request.CategorySaveRequest;
import com.blog.BlogBackend.dto.request.CategoryUpdateRequest;
import com.blog.BlogBackend.dto.response.CategoryResponse;
import com.blog.BlogBackend.entities.Category;
import com.blog.BlogBackend.repositories.CategoryRepository;
import com.blog.BlogBackend.services.abstracts.CategoryService;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryManager implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapperService modelMapperService;

    @Autowired
    public CategoryManager(CategoryRepository categoryRepository, ModelMapperService modelMapperService) {
        this.categoryRepository = categoryRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return Converter.getAllCategories(categoryRepository.findAll());
    }

    @Override
    public Category getCategoryByID(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("This category not exist!"));
    }

    @Override
    public CategoryResponse saveCategory(CategorySaveRequest categoryRequest) {
        Category category = modelMapperService.forRequest().map(categoryRequest, Category.class);
        return Converter.getCategory(category);
    }

    @Override
    public CategoryResponse updateCategory(CategoryUpdateRequest categoryUpdateRequest) {
        Category category = modelMapperService.forRequest().map(categoryUpdateRequest, Category.class);
        return Converter.getCategory(category);
    }

    @Override
    public CategoryResponse deleteCategory(long id) {
        Category category = getCategoryByID(id);
        categoryRepository.delete(category);
        return Converter.getCategory(category);
    }
}
