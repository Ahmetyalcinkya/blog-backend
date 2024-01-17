package com.blog.BlogBackend.utils;

import com.blog.BlogBackend.dto.response.AuthorityResponse;
import com.blog.BlogBackend.dto.response.CategoryResponse;
import com.blog.BlogBackend.entities.Authority;
import com.blog.BlogBackend.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    //AUTHORITY
    public static AuthorityResponse getAuthority(Authority authority){
        return new AuthorityResponse(authority.getId(), authority.getAuthority());
    }

    //CATEGORY
    public static List<CategoryResponse> getAllCategories(List<Category> categories){
        List<CategoryResponse> responses = new ArrayList<>();

        categories.forEach(category -> {
            CategoryResponse categoryResponse = new CategoryResponse(category.getId(), category.getTitle(), category.getImage(),category.getRating());
            responses.add(categoryResponse);
        });
        return responses;
    }
    public static CategoryResponse getCategory(Category category){
        return new CategoryResponse(category.getId(), category.getTitle(), category.getImage(), category.getRating());
    }
}
