package com.blog.blogbackend.services.impl;

import com.blog.blogbackend.entities.Category;
import com.blog.blogbackend.exceptions.ResourceNotFoundException;
import com.blog.blogbackend.payloads.CategoryDto;
import com.blog.blogbackend.repositories.CategoryRepository;
import com.blog.blogbackend.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.categoryRepository.save(this.categoryDtoToCategory(categoryDto));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", " Id: ", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        return this.categoryToCategoryDto(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", " Id: ", categoryId));
        this.categoryRepository.delete(category);

    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id:", categoryId));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return this.categoryRepository.findAll().stream().map((this::categoryToCategoryDto)).collect(Collectors.toList());
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto categoryToCategoryDto(Category category) {
        return this.modelMapper.map(category, CategoryDto.class);
    }
}
