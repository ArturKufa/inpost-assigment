package pl.arturkufa.product_discount.domain.product.policy;

import java.util.List;
import java.util.Optional;

public interface DiscountPolicy {

    Optional<ProductDiscount> calculatePriceBasedOnItemAmount(double price, int amount);

    List<ProductDiscount> getAllDiscounts(double price);

}
