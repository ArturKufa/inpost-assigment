package pl.arturkufa.product_discount.domain.product;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;
import pl.arturkufa.product_discount.domain.exception.TechnicalException;
import pl.arturkufa.product_discount.domain.product.policy.DiscountPolicy;
import pl.arturkufa.product_discount.domain.discount.DiscountType;
import pl.arturkufa.product_discount.domain.product.policy.ProductDiscount;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
public class Product {
    @Id
    private UUID id;
    private String name;
    private Double price;
    @Enumerated(value = EnumType.STRING)
    private DiscountType discountType;

    @Transient
    private DiscountPolicy discountPolicy;

    void setDiscountPolicy(final DiscountPolicy discountPolicy) {
        if(discountPolicy == null) {
            throw new TechnicalException("Discount policy can't be null");
        }
        this.discountPolicy = discountPolicy;
    }

   DiscountedProductDto getProductDiscount(@Nullable Integer amount) {
       List<ProductDiscount> productDiscounts = Optional.ofNullable(amount)
               .map(this::getProductDiscountsBasedOnAmount)
               .orElseGet(() -> discountPolicy.getAllDiscounts(this.price));

       return DiscountedProductDto.builder()
               .id(id)
               .name(name)
               .price(price)
               .discountType(discountType)
               .productDiscounts(productDiscounts)
               .build();
   }

    private List<ProductDiscount> getProductDiscountsBasedOnAmount(Integer amount) {
        return discountPolicy.calculatePriceBasedOnItemAmount(this.price, amount)
                .map(List::of)
                .orElse(Collections.emptyList());
    }


}
