package pl.arturkufa.product_discount.domain.discount;


import org.springframework.stereotype.Component;
import pl.arturkufa.product_discount.domain.exception.BusinessException;

@Component
class DiscountFactory {

    Discount createNewDiscount(DiscountDto discountDto) {
        validateDto(discountDto);
        return Discount.builder()
                .discountType(discountDto.getDiscountType())
                .discountValue(discountDto.getDiscountValue())
                .amount(discountDto.getAmount())
                .build();
    }

    private void validateDto(DiscountDto discountDto) {
        validateAmount(discountDto);
        validateValue(discountDto);
        validatePercentageValue(discountDto);
    }

    private void validateAmount(DiscountDto discountDto) {
        if(discountDto.getAmount() == null || discountDto.getAmount() < 0) {
            throw new BusinessException("Discount Amount can't be null or negative value");
        }
    }

    private void validateValue(DiscountDto discountDto) {
        if(discountDto.getDiscountValue() == null || discountDto.getDiscountValue() < 0) {
            throw new BusinessException("Discount Value can't be null or negative value");
        }
    }

    private void validatePercentageValue(DiscountDto discountDto) {
        if(discountDto.getDiscountType() == DiscountType.PERCENTAGE_BASED && discountDto.getDiscountValue() > 100) {
            throw new BusinessException("Percentage based discount Value can't be greater than 100%");
        }
    }
}

