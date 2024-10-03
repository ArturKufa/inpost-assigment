package pl.arturkufa.product_discount.domain.discount;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface DiscountJpaRepository extends JpaRepository<Discount, Long> {

    List<Discount> findAllByDiscountType(DiscountType discountType);
}
