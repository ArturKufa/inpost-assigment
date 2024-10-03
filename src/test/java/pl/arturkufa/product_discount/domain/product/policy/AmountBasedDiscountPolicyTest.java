package pl.arturkufa.product_discount.domain.product.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.arturkufa.product_discount.domain.discount.DiscountType;
import pl.arturkufa.product_discount.domain.discount.TestDiscount;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AmountBasedDiscountPolicyTest {

    private AmountBasedDiscountPolicy amountBasedDiscountPolicy;

    @BeforeEach
    void setup() {
        amountBasedDiscountPolicy = new AmountBasedDiscountPolicy(List.of(
                new TestDiscount(1L, DiscountType.AMOUNT_BASED, 5.0, 10),
                new TestDiscount(1L, DiscountType.AMOUNT_BASED, 20.0, 50),
                new TestDiscount(1L, DiscountType.AMOUNT_BASED, 50.0, 100)
        ));
    }

    @Test
    void shouldReturnDiscountedProductBasedOnAmount() {
        Optional<ProductDiscount> productDiscount = amountBasedDiscountPolicy.calculatePriceBasedOnItemAmount(100, 51);

        assertThat(productDiscount).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(ProductDiscount.builder()
                        .amount(50)
                        .value(20.0)
                        .itemPrice(80.0));
    }

    @Test
    void shouldReturnEmptyOptionalWhenThereIsNoDiscount() {
        Optional<ProductDiscount> productDiscount = amountBasedDiscountPolicy.calculatePriceBasedOnItemAmount(100, 7);

        assertThat(productDiscount).isEmpty();
    }

    @Test
    void shouldReturnAllDiscounts() {
        List<ProductDiscount> allDiscounts = amountBasedDiscountPolicy.getAllDiscounts(100);

        assertThat(allDiscounts).usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        ProductDiscount.builder()
                                .amount(10)
                                .value(5.0)
                                .itemPrice(95.0)
                                .build(),
                        ProductDiscount.builder()
                                .amount(50)
                                .value(20.0)
                                .itemPrice(80.0)
                                .build(),
                        ProductDiscount.builder()
                                .amount(100)
                                .value(50.0)
                                .itemPrice(50.0)
                                .build());
    }
}