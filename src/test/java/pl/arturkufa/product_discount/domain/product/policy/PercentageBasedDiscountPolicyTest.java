package pl.arturkufa.product_discount.domain.product.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.arturkufa.product_discount.domain.discount.DiscountType;
import pl.arturkufa.product_discount.domain.discount.TestDiscount;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PercentageBasedDiscountPolicyTest {
    private PercentageBasedDiscountPolicy percentageBasedDiscountPolicy;

    @BeforeEach
    void setup() {
        percentageBasedDiscountPolicy = new PercentageBasedDiscountPolicy(List.of(
                new TestDiscount(1L, DiscountType.PERCENTAGE_BASED, 2.0, 10),
                new TestDiscount(1L, DiscountType.PERCENTAGE_BASED, 3.0, 50),
                new TestDiscount(1L, DiscountType.PERCENTAGE_BASED, 5.0, 100)
        ));
    }

    @Test
    void shouldReturnDiscountedProductBasedOnAmount() {
        Optional<ProductDiscount> productDiscount = percentageBasedDiscountPolicy.calculatePriceBasedOnItemAmount(100, 51);

        assertThat(productDiscount).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(ProductDiscount.builder()
                        .amount(50)
                        .value(3.0)
                        .itemPrice(97.0));
    }

    @Test
    void shouldReturnEmptyOptionalWhenThereIsNoDiscount() {
        Optional<ProductDiscount> productDiscount = percentageBasedDiscountPolicy.calculatePriceBasedOnItemAmount(100, 7);

        assertThat(productDiscount).isEmpty();
    }

    @Test
    void shouldReturnAllDiscounts() {
        List<ProductDiscount> allDiscounts = percentageBasedDiscountPolicy.getAllDiscounts(100);

        assertThat(allDiscounts).usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        ProductDiscount.builder()
                                .amount(10)
                                .value(2.0)
                                .itemPrice(98.0)
                                .build(),
                        ProductDiscount.builder()
                                .amount(50)
                                .value(3.0)
                                .itemPrice(97.0)
                                .build(),
                        ProductDiscount.builder()
                                .amount(100)
                                .value(5.0)
                                .itemPrice(95.0)
                                .build());
    }
}
