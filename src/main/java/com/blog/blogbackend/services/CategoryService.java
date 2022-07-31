package com.blog.blogbackend.services;

import com.blog.blogbackend.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

    //delete
    void deleteCategory(int categoryId);

    //get
    CategoryDto getCategoryById(int categoryId);

    //getAll
    List<CategoryDto> getAllCategories();
}
