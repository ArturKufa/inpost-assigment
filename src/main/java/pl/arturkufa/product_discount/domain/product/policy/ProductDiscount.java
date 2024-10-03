package pl.arturkufa.product_discount.domain.product.policy;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProductDiscount {
    private Integer amount;
    private Double value;
    private Double itemPrice;
}
