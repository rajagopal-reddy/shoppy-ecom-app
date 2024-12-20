package com.ecom.shoppy.repository;

import com.ecom.shoppy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);

	boolean existsByName(String name);

}