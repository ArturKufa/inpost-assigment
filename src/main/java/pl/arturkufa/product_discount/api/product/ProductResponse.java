package pl.arturkufa.product_discount.api.product;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private Double price;
    private String discountType;
    private List<ProductDiscountResponse> discounts;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDiscountResponse {
        private Integer amount;
        private Double value;
        private Double itemPrice;
    }
}
