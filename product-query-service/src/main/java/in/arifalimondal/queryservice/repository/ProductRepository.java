package in.arifalimondal.queryservice.repository;

import in.arifalimondal.queryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
