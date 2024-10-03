package pl.arturkufa.product_discount.domain.product.policy;

import lombok.RequiredArgsConstructor;
import pl.arturkufa.product_discount.domain.discount.Discount;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class AmountBasedDiscountPolicy implements DiscountPolicy {
    private final List<Discount> discounts;

    @Override
    public Optional<ProductDiscount> calculatePriceBasedOnItemAmount(double price, int amount) {
        return discounts.stream()
                .sorted(Comparator.comparing(Discount::getAmount).reversed())
                .filter(discount -> discount.getAmount() <= amount)
                .findFirst()
                .map(discount -> ProductDiscount.builder()
                        .amount(discount.getAmount())
                        .value(discount.getDiscountValue())
                        .itemPrice(price - discount.getDiscountValue())
                        .build());

    }

    @Override
    public List<ProductDiscount> getAllDiscounts(double price) {
        return discounts.stream()
                .sorted(Comparator.comparing(Discount::getAmount))
                .map(discount -> ProductDiscount.builder()
                        .amount(discount.getAmount())
                        .value(discount.getDiscountValue())
                        .itemPrice(price - discount.getDiscountValue())
                        .build())
                .toList();
    }
}
