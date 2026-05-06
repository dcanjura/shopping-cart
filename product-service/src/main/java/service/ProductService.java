package service;

import domain.Product;
import dto.ProductRequest;
import dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductResponse saveProduct(ProductRequest request){
        return ProductMapper.toResponse(repository.save(Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .build()));
    }

    public ProductResponse getProduct(Long id){
        return ProductMapper.toResponse(repository.getProductById(id));
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable){
        return repository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    public ProductResponse updateProduct(ProductRequest request, Long id){
        return ProductMapper.toResponse(repository.save(new Product(id, request.name(), request.price(), request.stock())));
    }

    public String deleteProduct(String id){
        repository.deleteById(id);
        return "SUCCESS";
    }
}
