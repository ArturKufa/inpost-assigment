package pl.arturkufa.product_discount.domain.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.arturkufa.product_discount.ITConfig;
import pl.arturkufa.product_discount.api.product.ProductResponse;
import pl.arturkufa.product_discount.domain.discount.DiscountSpyMockAccesser;
import pl.arturkufa.product_discount.domain.discount.DiscountType;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ITConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "classpath:example-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProductIT extends DiscountSpyMockAccesser {

    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext
    void shouldReturnProductDiscounts() {
        ProductResponse response = get("http://localhost:" + port + "/v1/product/bc631743-d409-4333-bdaf-d92a5c683f4a/discount")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(ProductResponse.builder()
                        .id("bc631743-d409-4333-bdaf-d92a5c683f4a")
                        .name("Bike")
                        .price(550.0)
                        .discountType("PERCENTAGE_BASED")
                        .discounts(List.of(
                                ProductResponse.ProductDiscountResponse.builder()
                                        .amount(10)
                                        .value(2.0)
                                        .itemPrice(539.0)
                                        .build(),
                                ProductResponse.ProductDiscountResponse.builder()
                                        .amount(50)
                                        .value(3.0)
                                        .itemPrice(533.5)
                                        .build(),
                                ProductResponse.ProductDiscountResponse.builder()
                                        .amount(100)
                                        .value(4.5)
                                        .itemPrice(525.25)
                                        .build()))
                        .build());
    }

    @Test
    @DirtiesContext
    void shouldReturnSpecificProductDiscount() {
        ProductResponse response = get("http://localhost:" + port + "/v1/product/bc631743-d409-4333-bdaf-d92a5c683f4a/discount?amount=12")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(ProductResponse.builder()
                        .id("bc631743-d409-4333-bdaf-d92a5c683f4a")
                        .name("Bike")
                        .price(550.0)
                        .discountType("PERCENTAGE_BASED")
                        .discounts(List.of(
                                ProductResponse.ProductDiscountResponse.builder()
                                        .amount(10)
                                        .value(2.0)
                                        .itemPrice(539.0)
                                        .build()))
                        .build());
    }

    @Test
    @DirtiesContext
    void shouldCallJpaRepositoryOnlyOnceAndCacheResult() {
        ProductResponse firstResponse = get("http://localhost:" + port + "/v1/product/bc631743-d409-4333-bdaf-d92a5c683f4a/discount")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);

        ProductResponse secondResponse = get("http://localhost:" + port + "/v1/product/bc631743-d409-4333-bdaf-d92a5c683f4a/discount")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);

        assertThat(firstResponse).usingRecursiveComparison()
                .isEqualTo(secondResponse);
        validateDiscountJpaRepositoryCalledOnce(DiscountType.PERCENTAGE_BASED);
    }

    @Test
    @DirtiesContext
    @Sql(value = "classpath:delete-all-products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnBusinessExceptionWhenProductIsNotFound() {
        String response = given()
                .accept("text/plain;charset=UTF-8")
                .get("http://localhost:" + port + "/v1/product/bc631743-d409-4333-bdaf-d92a5c683f4a/discount")
                .then()
                .statusCode(400)
                .extract()
                .body()
                .asString();

        assertThat(response).isEqualTo("Business exception occurred: Can't find Product for given ID: bc631743-d409-4333-bdaf-d92a5c683f4a");
    }
}
