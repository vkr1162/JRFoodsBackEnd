package com.jrfoods.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrfoods.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p " +
		       "WHERE (:category IS NULL OR :category = '' OR p.category.name = :category) " +
		       "AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.discPrice BETWEEN :minPrice AND :maxPrice) " +
		       "AND (:minDiscount IS NULL OR p.discPercentage >= :minDiscount) " +
		       "ORDER BY " +
		       "CASE WHEN :sort = 'price_low' THEN p.discPrice END ASC, " +
		       "CASE WHEN :sort = 'price_high' THEN p.discPrice END DESC")
		List<Product> filterProducts(
		        @Param("category") String category,
		        @Param("minPrice") Integer minPrice,
		        @Param("maxPrice") Integer maxPrice,
		        @Param("minDiscount") Integer minDiscount,
		        @Param("sort") String sort);

}
