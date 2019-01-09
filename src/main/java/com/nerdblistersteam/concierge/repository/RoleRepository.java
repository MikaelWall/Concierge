package com.nerdblistersteam.concierge.repository;

import com.nerdblistersteam.concierge.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
