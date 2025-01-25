package com.jrfoods.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jrfoods.entity.Category;
import com.jrfoods.entity.Product;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.CreateProductBinding;
import com.jrfoods.repository.CategoryRepository;
import com.jrfoods.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	
	

	@Override
	public Product createProduct(CreateProductBinding productBinding) {
		
		Category topLevel = categoryRepo.findByName(productBinding.getTopLevelCategory());
		if(topLevel==null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(productBinding.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			topLevel = categoryRepo.save(topLevelCategory);
		}
		System.out.println("Top Level Game " + topLevel.getName());
		Category bottomLevel = categoryRepo.findByNameAndParent(productBinding.getBottomLevelCategory(),topLevel.getName());
		System.out.println("BottomLevel " + bottomLevel);
		if(bottomLevel==null) {
			Category bottomLevelCategory = new Category();
			bottomLevelCategory.setName(productBinding.getBottomLevelCategory());
			bottomLevelCategory.setParentCategory(topLevel);
			bottomLevelCategory.setLevel(2);
			bottomLevel = categoryRepo.save(bottomLevelCategory);
			System.out.println("Parent Category " + bottomLevel.getParentCategory());
		}
		
		
		Product product = new Product();
		product.setTitle(productBinding.getTitle());
		product.setBrand(productBinding.getBrand());
		product.setDescription(productBinding.getDescription());
		product.setDiscPrice(productBinding.getDiscountedPrice());
		product.setPrice(productBinding.getPrice());
		product.setDiscPercentage(productBinding.getDiscountPercentage());
		product.setImageUrl(productBinding.getImageUrl());
		product.setQuantity(productBinding.getQuantity());
		product.setCategory(bottomLevel);
		product.setSizes(productBinding.getSizes());
		product.setCreatedAt(LocalDateTime.now());
		Product productSaved = productRepo.save(product);
		return productSaved;
		
	}

	@Override
	public String deleteProduct(Integer productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepo.delete(product);
		
		return "Product deleted Successfully with Id" + productId;
	}

	@Override
	public Product updateProduct(Integer productId, Product productToUpdate) throws ProductException {
		
		Product product = findProductById(productId);
		if(productToUpdate.getQuantity()!=0) {
			product.setQuantity(productToUpdate.getQuantity());
		}
		
		return productRepo.save(product);
	}

	@Override
	public Product findProductById(Integer productId) throws ProductException {
		Optional<Product> opt = productRepo.findById(productId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product Not Found with Id" + productId);
	}

	@Override
	public Page<Product> getAllProducts(String Category, List<String> sizes, Integer minPrice, Integer maxPrice,
			Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		
		Sort sorting = Sort.unsorted();
		    if ("price_low".equals(sort)) {
		        sorting = Sort.by("discPrice").ascending();
		    } else if ("price_high".equals(sort)) {
		        sorting = Sort.by("discPrice").descending();
		    }
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting);
		
		List<Product> products = productRepo.filterProducts(Category, minPrice, maxPrice, minDiscount, sort);
		if(stock!=null) {
			if(stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity()>0).collect(Collectors.toList());
			}
			else if(stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
			}
		}
		int startIndex = (int)pageable.getOffset();
		int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());
		if (startIndex > endIndex) {
	        return new PageImpl<>(List.of(), pageable, products.size());
	    }
		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<Product>(pageContent,pageable,products.size());
		return filteredProducts;
	}

	@Override
	public List<Product> getProductByCategory(String Category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> productsList = productRepo.findAll();
		return productsList;
	}

}
