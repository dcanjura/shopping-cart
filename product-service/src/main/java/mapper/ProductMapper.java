package mapper;

import domain.Product;
import dto.ProductResponse;

public class ProductMapper {

    public static ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getPrice()
        );
    }
}
