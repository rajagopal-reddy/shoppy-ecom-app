package com.ecom.shoppy.service.product;

import com.ecom.shoppy.dto.ProductDto;
import com.ecom.shoppy.model.Product;
import com.ecom.shoppy.request.AddProductRequest;
import com.ecom.shoppy.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {

	Product addProduct(AddProductRequest request);
	
	Product getProductById (Long id);
	void deleteProductById(Long id);
	Product updateProduct(UpdateProductRequest request, Long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand, String name);
	Long countProductsByBrandAndName(String brand, String name);

	ProductDto convertToDto(Product product);

	List<ProductDto> getConvertedProducts(List<Product> products);

	
}