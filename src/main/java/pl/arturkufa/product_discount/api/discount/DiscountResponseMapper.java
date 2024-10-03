package pl.arturkufa.product_discount.api.discount;

import org.springframework.stereotype.Component;
import pl.arturkufa.product_discount.domain.discount.DiscountDto;
import pl.arturkufa.product_discount.domain.discount.DiscountType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class DiscountResponseMapper {

    DiscountResponse mapToResponse(List<DiscountDto> discountDtos) {
        List<DiscountResponse.CategorizedDiscount> categorizedDiscounts = discountDtos.stream()
                .collect(Collectors.groupingBy(DiscountDto::getDiscountType))
                .entrySet()
                .stream()
                .map(this::mapToCategorizedDiscount)
                .toList();

        return new DiscountResponse(categorizedDiscounts);
    }

    List<DiscountDto> mapToDto(DiscountRequest discountRequest) {
        return discountRequest.getCategorizedDiscounts()
                .stream()
                .flatMap(this::mapCategorizedDiscountsToDtos)
                .toList();
    }

    private Stream<DiscountDto> mapCategorizedDiscountsToDtos(DiscountRequest.CategorizedDiscount categorizedDiscount) {
        return categorizedDiscount.getDiscounts()
                .stream()
                .map(simplifiedDiscount -> DiscountDto.builder()
                        .discountType(categorizedDiscount.getDiscountType())
                        .discountValue(simplifiedDiscount.getDiscountValue())
                        .amount(simplifiedDiscount.getAmount())
                        .build());
    }

    private DiscountResponse.CategorizedDiscount mapToCategorizedDiscount(Map.Entry<DiscountType, List<DiscountDto>> entry) {
        return DiscountResponse.CategorizedDiscount.builder()
                .discountType(entry.getKey())
                .discounts(entry.getValue()
                        .stream()
                        .map(this::mapToSimplifiedDiscount)
                        .toList())
                .build();
    }

    private DiscountResponse.SimplifiedDiscount mapToSimplifiedDiscount(DiscountDto discountDto) {
        return DiscountResponse.SimplifiedDiscount.builder()
                .discountValue(discountDto.getDiscountValue())
                .amount(discountDto.getAmount())
                .build();
    }
}
