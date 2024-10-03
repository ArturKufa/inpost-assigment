package pl.arturkufa.product_discount.api.discount;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arturkufa.product_discount.domain.discount.DiscountService;




@RestController
@RequestMapping("/v1/discount")
public class DiscountApi {

    private final DiscountService discountService;
    private final DiscountResponseMapper discountResponseMapper;

    @Autowired
    public DiscountApi(DiscountService discountService, DiscountResponseMapper discountResponseMapper) {
        this.discountService = discountService;
        this.discountResponseMapper = discountResponseMapper;
    }

    @GetMapping
    ResponseEntity<DiscountResponse> getProductDiscount() {
        DiscountResponse discountResponse = discountResponseMapper.mapToResponse(discountService.getAllDiscounts());

        return ResponseEntity.ok(discountResponse);
    }

    @PutMapping
    ResponseEntity<Void> updateProductDiscount(@Valid @RequestBody DiscountRequest discountRequest) {
        discountService.updateDiscounts(discountResponseMapper.mapToDto(discountRequest));
        return ResponseEntity.ok(null);
    }
}
