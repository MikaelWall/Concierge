package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.Role;
import com.nerdblistersteam.concierge.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
    //test
}
