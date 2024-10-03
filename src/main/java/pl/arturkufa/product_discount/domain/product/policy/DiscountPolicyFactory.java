package pl.arturkufa.product_discount.domain.product.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.arturkufa.product_discount.domain.discount.Discount;
import pl.arturkufa.product_discount.domain.discount.DiscountRepository;
import pl.arturkufa.product_discount.domain.discount.DiscountType;
import pl.arturkufa.product_discount.domain.exception.TechnicalException;

import java.util.List;

@Component
public class DiscountPolicyFactory {

    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountPolicyFactory(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public DiscountPolicy getDiscountPolicy(final DiscountType discountType) {
        final List<Discount> discounts = discountRepository.findAllByDiscountType(discountType);
        return switch (discountType) {
            case PERCENTAGE_BASED -> new PercentageBasedDiscountPolicy(discounts);
            case AMOUNT_BASED -> new AmountBasedDiscountPolicy(discounts);
            default -> throw new TechnicalException("Unsupported discount type %s. Can't resolve discount policy".formatted(discountType));
        };

    }
}
