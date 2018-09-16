package com.example.demo.repos;

        import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);

    User findByActivationCode(String code);
}
