package com.jrfoods.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrfoods.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
	public Category findByName(String name);
	
	@Query("SELECT c FROM Category c " +
		       "WHERE c.name = :name AND " +
		       "(c.parentCategory.name = :parentCategoryName OR (:parentCategoryName IS NULL AND c.parentCategory IS NULL))")
		public Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);



}
