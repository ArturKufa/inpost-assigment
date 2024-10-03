package pl.arturkufa.product_discount.api.product;

import org.springframework.stereotype.Component;
import pl.arturkufa.product_discount.domain.product.DiscountedProductDto;

import java.util.stream.Collectors;

@Component
class ProductResponseMapper {

    ProductResponse mapToResponse(DiscountedProductDto discountedProductDto) {
        return ProductResponse.builder()
                .id(discountedProductDto.getId().toString())
                .name(discountedProductDto.getName())
                .price(discountedProductDto.getPrice())
                .discountType(discountedProductDto.getDiscountType().name())
                .discounts(discountedProductDto.getProductDiscounts()
                        .stream()
                        .map(productDiscount -> ProductResponse.ProductDiscountResponse.builder()
                                .value(productDiscount.getValue())
                                .amount(productDiscount.getAmount())
                                .itemPrice(productDiscount.getItemPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
