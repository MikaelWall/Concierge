package com.nerdblistersteam.concierge.repository;

import com.nerdblistersteam.concierge.domain.Description;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

    Optional<Description> findByTag(String tag);
}
