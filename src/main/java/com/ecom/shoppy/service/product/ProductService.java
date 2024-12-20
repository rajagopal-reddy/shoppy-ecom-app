package com.ecom.shoppy.service.product;

import com.ecom.shoppy.dto.ImageDto;
import com.ecom.shoppy.dto.ProductDto;
import com.ecom.shoppy.exception.AlreadyExistsExceptoin;
import com.ecom.shoppy.exception.ResourceNotFoundException;
import com.ecom.shoppy.model.Category;
import com.ecom.shoppy.model.Image;
import com.ecom.shoppy.model.Product;
import com.ecom.shoppy.repository.CategoryRepository;
import com.ecom.shoppy.repository.ImageRepository;
import com.ecom.shoppy.repository.ProductRepository;
import com.ecom.shoppy.request.AddProductRequest;
import com.ecom.shoppy.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
    	
    	if (productExists(request.getName(), request.getBrand())) {
			throw new AlreadyExistsExceptoin(request.getBrand()+""+request.getName()+" already exists, you can update this product instead!" );
		}
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }
    
    private boolean productExists(String name, String brand) {
    	return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Product not found!");
                        });
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
    
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
    	return products.stream().map(this::convertToDto)
    			.toList();
    }
    
    @Override
    public ProductDto convertToDto(Product product) {
    	ProductDto productDto = modelMapper.map(product, ProductDto.class);
    	List<Image> images = imageRepository.findByProductId(product.getId());
    	List<ImageDto> imageDtos = images.stream()
    			.map(image -> modelMapper.map(images, ImageDto.class))
    			.toList();
    	productDto.setImages(imageDtos);
    	return productDto;
    }
    
}