package pl.arturkufa.product_discount.domain.discount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DiscountRepository {
    private final DiscountJpaRepository discountJpaRepository;

    @Autowired
    public DiscountRepository(DiscountJpaRepository discountJpaRepository) {
        this.discountJpaRepository = discountJpaRepository;
    }

    @Cacheable(cacheNames = "discounts")
    public List<Discount> findAllByDiscountType(DiscountType discountType) {
        log.debug("Using jpa repository to fill cache");
        return discountJpaRepository.findAllByDiscountType(discountType);
    }

    @Cacheable(cacheNames = "discounts")
    public List<Discount> findAll() {
        log.debug("Using jpa repository to fill cache");
        return discountJpaRepository.findAll();
    }

    public void deleteAll() {
        discountJpaRepository.deleteAll();
    }

    public void saveAll(List<Discount> discounts) {
        discountJpaRepository.saveAll(discounts);
    }
}
