package pl.arturkufa.product_discount.api.discount;

import lombok.*;
import pl.arturkufa.product_discount.domain.discount.DiscountType;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {
    private List<CategorizedDiscount> categorizedDiscounts;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorizedDiscount {
        private DiscountType discountType;
        private List<SimplifiedDiscount> discounts;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimplifiedDiscount {
        private Double discountValue;
        private Integer amount;
    }
}
