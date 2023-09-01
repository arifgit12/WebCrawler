package in.arifalimondal.auth.repository;

import in.arifalimondal.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByName(String username);
}
