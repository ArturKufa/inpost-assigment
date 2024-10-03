package pl.arturkufa.product_discount.domain.product;

import lombok.Builder;
import lombok.Getter;
import pl.arturkufa.product_discount.domain.discount.DiscountType;
import pl.arturkufa.product_discount.domain.product.policy.ProductDiscount;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DiscountedProductDto {
    private UUID id;
    private String name;
    private Double price;
    private DiscountType discountType;
    private List<ProductDiscount> productDiscounts;
}
