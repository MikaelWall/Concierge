package com.nerdblistersteam.concierge.repository;

import com.nerdblistersteam.concierge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByFirstName(String firstName);

    Optional<User> findByEmailAndActivationCode(String email, String activationCode);
}
