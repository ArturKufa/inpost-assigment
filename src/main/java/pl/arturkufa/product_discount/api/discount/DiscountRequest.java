package pl.arturkufa.product_discount.api.discount;


import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import pl.arturkufa.product_discount.domain.discount.DiscountType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiscountRequest {
    @Nonnull
    private List<@Valid CategorizedDiscount> categorizedDiscounts;

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorizedDiscount {
        @Nonnull
        private DiscountType discountType;
        
        private List<@Valid SimplifiedDiscount> discounts;
    }

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimplifiedDiscount {
        @PositiveOrZero
        private Double discountValue;
        @PositiveOrZero
        private Integer amount;
    }
}
