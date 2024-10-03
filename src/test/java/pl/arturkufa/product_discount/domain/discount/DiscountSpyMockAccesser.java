package pl.arturkufa.product_discount.domain.discount;

import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.arturkufa.product_discount.domain.discount.DiscountJpaRepository;
import pl.arturkufa.product_discount.domain.discount.DiscountType;

import static org.mockito.Mockito.verify;

public class DiscountSpyMockAccesser {
    @SpyBean
    private DiscountJpaRepository discountJpaRepository;

    public void validateDiscountJpaRepositoryCalledOnce(DiscountType discountType) {
        verify(discountJpaRepository).findAllByDiscountType(discountType);
    }
}
