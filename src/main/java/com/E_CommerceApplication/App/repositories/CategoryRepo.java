package com.E_CommerceApplication.App.repositories;

import com.E_CommerceApplication.App.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepo extends JpaRepository<Categories, Long> {

    Categories findByCategoryName(String categoryName);

}
