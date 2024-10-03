package pl.arturkufa.product_discount.domain.product.policy;

import lombok.RequiredArgsConstructor;
import pl.arturkufa.product_discount.domain.discount.Discount;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class PercentageBasedDiscountPolicy implements DiscountPolicy {
    private static final double NO_DISCOUNT = 1;
    private static final int ONE_HUNDRED_PERCENT = 100;
    private final List<Discount> discounts;

    @Override
    public Optional<ProductDiscount> calculatePriceBasedOnItemAmount(double price, int amount) {
        return discounts.stream()
                .sorted(Comparator.comparing(Discount::getAmount).reversed())
                .filter(d -> d.getAmount() <= amount)
                .findFirst()
                .map(discount -> mapToProductDiscount(price, discount));
    }

    @Override
    public List<ProductDiscount> getAllDiscounts(double price) {
        return discounts.stream()
                .sorted(Comparator.comparing(Discount::getAmount))
                .map(discount -> mapToProductDiscount(price, discount))
                .toList();
    }

    private ProductDiscount mapToProductDiscount(double price, Discount discount) {
        return ProductDiscount.builder()
                .amount(discount.getAmount())
                .value(discount.getDiscountValue())
                .itemPrice(price * getPercentageModifier(discount))
                .build();
    }

    private  Double getPercentageModifier(Discount discount) {
        return (ONE_HUNDRED_PERCENT - discount.getDiscountValue()) / ONE_HUNDRED_PERCENT;
    }
}
