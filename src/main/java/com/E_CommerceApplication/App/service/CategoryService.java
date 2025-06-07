package com.E_CommerceApplication.App.service;



import com.E_CommerceApplication.App.DTOs.CategoryDTO;
import com.E_CommerceApplication.App.DTOs.CategoryResponse;
import com.E_CommerceApplication.App.models.Categories;

public interface CategoryService{
    CategoryDTO createCategory(Categories category);

	CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	CategoryDTO updateCategory(Categories category, Long categoryId);

	String deleteCategory(Long categoryId);
}
