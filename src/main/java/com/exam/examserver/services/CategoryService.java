package com.exam.examserver.services;

import com.exam.examserver.entities.Category;
import com.exam.examserver.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,int categoryId);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategory(int categoryId);
    void deleteCategory(int categoryId);
    Category categoryDtoToCategory(CategoryDto categoryDto);
    CategoryDto categoryToCategoryDto(Category category);

}
