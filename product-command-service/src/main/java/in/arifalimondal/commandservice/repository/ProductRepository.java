package in.arifalimondal.commandservice.repository;

import in.arifalimondal.commandservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
