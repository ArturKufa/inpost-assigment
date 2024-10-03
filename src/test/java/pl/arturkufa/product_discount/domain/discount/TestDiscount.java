package pl.arturkufa.product_discount.domain.discount;

import pl.arturkufa.product_discount.domain.discount.Discount;
import pl.arturkufa.product_discount.domain.discount.DiscountType;

public class TestDiscount extends Discount {

    public TestDiscount(Long id, DiscountType discountType, Double discountValue, Integer amount) {
        super(id, discountType, discountValue, amount);
    }
}
