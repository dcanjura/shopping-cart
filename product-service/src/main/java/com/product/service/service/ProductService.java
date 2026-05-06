package com.product.service.service;

import com.product.service.domain.Product;
import com.product.service.dto.ProductRequest;
import com.product.service.dto.ProductResponse;
import com.product.service.exception.BadRequestException;
import com.product.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.product.service.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.product.service.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    /**
     * Creates a new product and saves it to the DB
     * @param request
     * @return ProductResponse
     */
    public ProductResponse saveProduct(ProductRequest request){
        return ProductMapper.toResponse(repository.save(Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .build()));
    }

    /**
     * Retrieves all products based on pagination request
     * @param pageable
     * @return Pages of ProductResponse values
     */
    public Page<ProductResponse> getAllProducts(Pageable pageable){
        return repository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    /**
     * Gets a product based on ID
     * @param id
     * @return ProductResponse
     */
    public ProductResponse getProduct(Long id){
        return ProductMapper.toResponse(repository.getProductById(id));
    }

    /**
     * Reduces stock whenever a purchase is made successfully
     * @param productId
     * @param quantity
     */
    public void reduceStock(String productId, Integer quantity) {
        if(productId == null || productId.isBlank()){
            throw new BadRequestException("productId is required");
        }

        Product product = repository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (product.getStock() < quantity) {
            throw new BadRequestException("Not enough stock");
        }

        product.setStock(product.getStock() - quantity);

        repository.save(product);
    }

    /**
     * Updates a product based on id and request bodu
     * @param request
     * @param id
     * @return new ProductResponse
     */
    public ProductResponse updateProduct(ProductRequest request, Long id){
        return ProductMapper.toResponse(repository.save(new Product(id, request.name(), request.price(), request.stock())));
    }

    /**
     * Deletes a product from the DB
     * @param id
     * @return either success or throws exception when no record found
     */
    public String deleteProduct(String id){
        if(repository.getProductById(Long.valueOf(id)) != null){
            repository.deleteById(id);
            return "SUCCESS";
        }else {
            throw new NotFoundException("No product found with id: " + id);
        }
    }
}
