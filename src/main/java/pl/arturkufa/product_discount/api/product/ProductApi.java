package pl.arturkufa.product_discount.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arturkufa.product_discount.domain.product.DiscountedProductDto;
import pl.arturkufa.product_discount.domain.product.ProductService;


@RestController
@RequestMapping("/v1/product")
public class ProductApi {

    private final ProductService productService;
    private final ProductResponseMapper productResponseMapper;

    @Autowired
    public ProductApi(ProductService productService, ProductResponseMapper productResponseMapper) {
        this.productService = productService;
        this.productResponseMapper = productResponseMapper;
    }

    @GetMapping("/{id}/discount")
    ResponseEntity<ProductResponse> getProductDiscount(@PathVariable("id") String id, @RequestParam(value = "amount", required = false) Integer amount) {

        DiscountedProductDto productDiscount = productService.getProductDiscount(id, amount);

        return ResponseEntity.ok(productResponseMapper.mapToResponse(productDiscount));
    }

}
