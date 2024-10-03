package pl.arturkufa.product_discount.domain.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import pl.arturkufa.product_discount.domain.exception.BusinessException;
import pl.arturkufa.product_discount.domain.product.policy.DiscountPolicy;
import pl.arturkufa.product_discount.domain.product.policy.DiscountPolicyFactory;

import java.util.UUID;

@Repository
class ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final DiscountPolicyFactory discountPolicyFactory;

    @Autowired
    public ProductRepository(ProductJpaRepository productJpaRepository, DiscountPolicyFactory discountPolicyFactory) {
        this.productJpaRepository = productJpaRepository;
        this.discountPolicyFactory = discountPolicyFactory;
    }

    @Cacheable("products")
    Product findById(UUID id) {
        final Product product = productJpaRepository.findById(id).orElseThrow(() -> new BusinessException("Can't find Product for given ID: %s".formatted(id)));
        final DiscountPolicy discountPolicy = discountPolicyFactory.getDiscountPolicy(product.getDiscountType());

        product.setDiscountPolicy(discountPolicy);

        return product;
    }
}
