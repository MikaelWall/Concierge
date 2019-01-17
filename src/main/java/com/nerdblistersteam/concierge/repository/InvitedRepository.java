package com.nerdblistersteam.concierge.repository;

import com.nerdblistersteam.concierge.domain.Invited;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitedRepository extends JpaRepository<Invited, Long> {

//    List<Invited> findAll();

    Optional<Invited> findByEmail(String email);

    Optional<Invited> findByEmailAndActivationCode(String email, String activationCode);
}
