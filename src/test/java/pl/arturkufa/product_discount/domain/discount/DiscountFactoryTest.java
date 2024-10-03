package pl.arturkufa.product_discount.domain.discount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.arturkufa.product_discount.domain.discount.DiscountDto;
import pl.arturkufa.product_discount.domain.discount.DiscountFactory;
import pl.arturkufa.product_discount.domain.discount.DiscountType;
import pl.arturkufa.product_discount.domain.exception.BusinessException;

import static org.assertj.core.api.Assertions.*;

class DiscountFactoryTest {

    private DiscountFactory discountFactory;

    @BeforeEach
    void setup() {
        discountFactory = new DiscountFactory();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10})
    @NullSource
    void shouldThrowExceptionWhenAmountItInvalid(Integer amount) {
        BusinessException businessException = catchThrowableOfType(() -> discountFactory.createNewDiscount(DiscountDto.builder()
                .discountType(DiscountType.PERCENTAGE_BASED)
                .discountValue(10.0)
                .amount(amount)
                .build()), BusinessException.class);

        assertThat(businessException).hasMessage("Discount Amount can't be null or negative value");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -10.0, -0.00001})
    @NullSource
    void shouldThrowExceptionWhenValueIsInvalid(Double value) {
        BusinessException businessException = catchThrowableOfType(() -> discountFactory.createNewDiscount(DiscountDto.builder()
                .discountType(DiscountType.AMOUNT_BASED)
                .discountValue(value)
                .amount(10)
                .build()), BusinessException.class);

        assertThat(businessException).hasMessage("Discount Value can't be null or negative value");
    }

    @Test
    void shouldThrowExceptionWhenPercentageBasedDiscountValueIsGreaterThanZero() {
        BusinessException businessException = catchThrowableOfType(() -> discountFactory.createNewDiscount(DiscountDto.builder()
                .discountType(DiscountType.PERCENTAGE_BASED)
                .discountValue(101.0)
                .amount(666)
                .build()), BusinessException.class);

        assertThat(businessException).hasMessage("Percentage based discount Value can't be greater than 100%");
    }

    @Test
    void shouldPassValidationForValueGreaterThanZeroWhenDiscountIsNotPercentageBased() {
        assertThatCode(() -> discountFactory.createNewDiscount(DiscountDto.builder()
                .discountType(DiscountType.AMOUNT_BASED)
                .discountValue(101.0)
                .amount(15)
                .build())).doesNotThrowAnyException();

    }

}