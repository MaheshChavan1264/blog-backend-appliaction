package com.blog.blogbackend.controller;

import com.blog.blogbackend.payloads.ApiResponse;
import com.blog.blogbackend.payloads.CategoryDto;
import com.blog.blogbackend.services.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    //GET - get all categories
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = this.categoryServiceImpl.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    //GET - get single category by id
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "categoryId") int categoryId) {
        return new ResponseEntity<>(this.categoryServiceImpl.getCategoryById(categoryId), HttpStatus.OK);

    }

    //POST - create Category
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(this.categoryServiceImpl.createCategory(categoryDto), HttpStatus.CREATED);
    }

    //PUT - update Category
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int categoryId) {
        return new ResponseEntity<>(this.categoryServiceImpl.updateCategory(categoryDto, categoryId), HttpStatus.OK);
    }

    //DELETE - Delete category
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId) {
        this.categoryServiceImpl.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
    }
}
