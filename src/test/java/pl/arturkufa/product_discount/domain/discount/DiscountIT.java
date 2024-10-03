package pl.arturkufa.product_discount.domain.discount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.arturkufa.product_discount.ITConfig;
import pl.arturkufa.product_discount.api.discount.DiscountRequest;
import pl.arturkufa.product_discount.api.discount.DiscountResponse;
import pl.arturkufa.product_discount.domain.discount.DiscountJpaRepository;
import pl.arturkufa.product_discount.domain.discount.DiscountType;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ITConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "classpath:example-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DiscountIT {
    @LocalServerPort
    private int port;

    @SpyBean
    private DiscountJpaRepository discountJpaRepository;

    @Test
    @DirtiesContext
    void shouldReturnAllDiscounts() {
        DiscountResponse response = get("http://localhost:" + port + "/v1/discount")
                .then()
                .statusCode(200)
                .extract()
                .as(DiscountResponse.class);

        assertThat(response).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new DiscountResponse(
                        List.of(DiscountResponse.CategorizedDiscount.builder()
                                        .discountType(DiscountType.PERCENTAGE_BASED)
                                        .discounts(List.of(
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(2.0)
                                                        .amount(10)
                                                        .build(),
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(3.0)
                                                        .amount(50)
                                                        .build(),
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(4.5)
                                                        .amount(100)
                                                        .build()))
                                        .build(),
                                DiscountResponse.CategorizedDiscount.builder()
                                        .discountType(DiscountType.AMOUNT_BASED)
                                        .discounts(List.of(
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(10.0)
                                                        .amount(10)
                                                        .build(),
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(20.0)
                                                        .amount(50)
                                                        .build(),
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(25.0)
                                                        .amount(100)
                                                        .build()))
                                        .build()
                        )));
    }

    @Test
    @DirtiesContext
    void shouldCacheResultsAndAskRepositoryOnlyOnce() {
        DiscountResponse firstResponse = get("http://localhost:" + port + "/v1/discount")
                .then()
                .statusCode(200)
                .extract()
                .as(DiscountResponse.class);

        DiscountResponse secondResponse = get("http://localhost:" + port + "/v1/discount")
                .then()
                .statusCode(200)
                .extract()
                .as(DiscountResponse.class);

        assertThat(firstResponse).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(secondResponse);
        verify(discountJpaRepository).findAll();
    }

    @Test
    @DirtiesContext
    void shouldUpdateDiscountsAndClearCache() {
        DiscountResponse responseBeforeChange = get("http://localhost:" + port + "/v1/discount")
                .then()
                .statusCode(200)
                .extract()
                .as(DiscountResponse.class);

        assertThat(responseBeforeChange).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new DiscountResponse(
                        List.of(DiscountResponse.CategorizedDiscount.builder()
                                        .discountType(DiscountType.PERCENTAGE_BASED)
                                        .discounts(List.of(
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(2.0)
                                                        .amount(10)
                                                        .build(),
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(3.0)
                                                        .amount(50)
                                                        .build(),
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(4.5)
                                                        .amount(100)
                                                        .build()))
                                        .build(),
                                DiscountResponse.CategorizedDiscount.builder()
                                        .discountType(DiscountType.AMOUNT_BASED)
                                        .discounts(List.of(
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(10.0)
                                                        .amount(10)
                                                        .build(),
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(20.0)
                                                        .amount(50)
                                                        .build(),
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(25.0)
                                                        .amount(100)
                                                        .build()))
                                        .build()
                        )));
        given()
                .contentType("application/json")
                .body(new DiscountRequest(List.of(
                        DiscountRequest.CategorizedDiscount.builder()
                                .discountType(DiscountType.PERCENTAGE_BASED)
                                .discounts(List.of(
                                        DiscountRequest.SimplifiedDiscount.builder()
                                                .discountValue(99.9)
                                                .amount(10)
                                                .build()))
                                .build()

                )))
                .put("http://localhost:" + port + "/v1/discount")
                .then()
                .statusCode(200);


        DiscountResponse responseAfterChange = get("http://localhost:" + port + "/v1/discount")
                .then()
                .statusCode(200)
                .extract()
                .as(DiscountResponse.class);

        assertThat(responseAfterChange).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new DiscountResponse(
                        List.of(DiscountResponse.CategorizedDiscount.builder()
                                        .discountType(DiscountType.PERCENTAGE_BASED)
                                        .discounts(List.of(
                                                DiscountResponse.SimplifiedDiscount.builder()
                                                        .discountValue(99.9)
                                                        .amount(10)
                                                        .build()))
                                        .build())));
        verify(discountJpaRepository, times(2)).findAll();
    }
}
