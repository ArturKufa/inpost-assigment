package pl.arturkufa.product_discount.domain.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountFactory discountFactory;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, DiscountFactory discountFactory) {
        this.discountRepository = discountRepository;
        this.discountFactory = discountFactory;
    }

    public List<DiscountDto> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(discount -> mapToDto(discount))
                .toList();
    }

    @Transactional
    @CacheEvict(value="discounts", allEntries = true)
    public void updateDiscounts(List<DiscountDto> discountDtos) {
        discountRepository.deleteAll();
        List<Discount> discounts = discountDtos.stream()
                .map(discountFactory::createNewDiscount)
                .toList();
        discountRepository.saveAll(discounts);
    }

    private DiscountDto mapToDto(Discount discount) {
        return DiscountDto.builder()
                .discountType(discount.getDiscountType())
                .discountValue(discount.getDiscountValue())
                .amount(discount.getAmount())
                .build();
    }
}
