package com.E_CommerceApplication.App.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.E_CommerceApplication.App.DTOs.CategoryDTO;
import com.E_CommerceApplication.App.DTOs.CategoryResponse;
import com.E_CommerceApplication.App.exception.APIException;
import com.E_CommerceApplication.App.exception.ResourceNotFoundException;
import com.E_CommerceApplication.App.models.Categories;
import com.E_CommerceApplication.App.models.Product;
import com.E_CommerceApplication.App.repositories.CategoryRepo;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CategoryServiceImp implements CategoryService {

    
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(Categories category) {
		Categories savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());

		if (savedCategory != null) {
			throw new APIException("Category with the name '" + category.getCategoryName() + "' already exists !!!");
		}

		savedCategory = categoryRepo.save(category);

		return modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		
		Page<Categories> pageCategories = categoryRepo.findAll(pageDetails);

		List<Categories> categories = pageCategories.getContent();

		if (categories.size() == 0) {
			throw new APIException("No category is created till now");
		}

		List<CategoryDTO> categoryDTOs = categories.stream()
				.map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());

		CategoryResponse categoryResponse = new CategoryResponse();
		
		categoryResponse.setContent(categoryDTOs);
		categoryResponse.setPageNumber(pageCategories.getNumber());
		categoryResponse.setPageSize(pageCategories.getSize());
		categoryResponse.setTotalElements(pageCategories.getTotalElements());
		categoryResponse.setTotalPages(pageCategories.getTotalPages());
		categoryResponse.setLastPage(pageCategories.isLast());
		
		return categoryResponse;
	}

	@Override
	public CategoryDTO updateCategory(Categories category, Long categoryId) {
		Categories savedCategory = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		category.setCategoryId(categoryId);

		savedCategory = categoryRepo.save(category);

		return modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		Categories category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		List<Product> products = category.getProduct();

		products.forEach(product -> {
			productService.deleteProduct(product.getProductId());
		});
		
		categoryRepo.delete(category);

		return "Category with categoryId: " + categoryId + " deleted successfully !!!";
	}

}
