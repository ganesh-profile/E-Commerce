package com.E_CommerceApplication.App.repositories;

import com.E_CommerceApplication.App.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameLike(String keyword, Pageable pageDetails);
}
