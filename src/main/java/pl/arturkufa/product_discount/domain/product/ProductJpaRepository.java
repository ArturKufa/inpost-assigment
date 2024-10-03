package pl.arturkufa.product_discount.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface ProductJpaRepository extends JpaRepository<Product, UUID> {
}
