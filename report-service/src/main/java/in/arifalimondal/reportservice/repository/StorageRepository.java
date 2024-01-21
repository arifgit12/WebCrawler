package in.arifalimondal.reportservice.repository;

import in.arifalimondal.reportservice.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByName(String name);
}
