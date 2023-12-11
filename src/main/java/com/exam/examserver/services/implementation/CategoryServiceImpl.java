package com.exam.examserver.services.implementation;

import com.exam.examserver.entities.Category;
import com.exam.examserver.exception.ResourceFoundException;
import com.exam.examserver.exception.ResourceNotFoundException;
import com.exam.examserver.payloads.CategoryDto;
import com.exam.examserver.repositories.CategoryRepository;
import com.exam.examserver.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper , CategoryRepository categoryRepository){
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if(categoryRepository.findByTitle(categoryDto.getTitle()).isPresent()){
            System.out.println("Category Already Exists.");
            throw new ResourceFoundException("Category","Title",categoryDto.getTitle());
        }
        Category category = this.categoryDtoToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return this.categoryToCategoryDto(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto,int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","ID",String.valueOf(categoryId)));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return this.categoryToCategoryDto(updatedCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::categoryToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","ID",String.valueOf(categoryId)));
        return this.categoryToCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","ID",String.valueOf(categoryId)));
        categoryRepository.delete(category);
    }

    @Override
    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto,Category.class);
    }

    @Override
    public CategoryDto categoryToCategoryDto(Category category) {
        return modelMapper.map(category,CategoryDto.class);
    }

}
