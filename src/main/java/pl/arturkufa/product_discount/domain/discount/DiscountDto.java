package pl.arturkufa.product_discount.domain.discount;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DiscountDto {
    private DiscountType discountType;
    private Double discountValue;
    private Integer amount;
}
