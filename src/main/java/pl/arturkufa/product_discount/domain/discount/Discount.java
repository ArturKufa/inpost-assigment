package pl.arturkufa.product_discount.domain.discount;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
public class Discount {
    @Id
    @SequenceGenerator(name = "DISCOUNT_SEQ", sequenceName = "DISCOUNT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISCOUNT_SEQ")
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private DiscountType discountType;
    private Double discountValue;
    private Integer amount;

}
