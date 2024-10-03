package pl.arturkufa.product_discount.domain.product;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;
import pl.arturkufa.product_discount.domain.exception.BusinessException;

import java.util.UUID;

@Component
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public DiscountedProductDto getProductDiscount(String productId, @Nullable Integer amount) {
        if(productId == null) {
            throw new BusinessException("ProductId not provided");
        }
        Product product = productRepository.findById(mapToUUID(productId));

        return product.getProductDiscount(amount);
    }

    private UUID mapToUUID(String productId) {
        try {
            return UUID.fromString(productId);
        } catch (Exception e) {
            throw new BusinessException("Given productId:%s is not in UUID format.".formatted(productId));
        }
    }
}
