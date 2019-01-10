package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.Description;
import com.nerdblistersteam.concierge.repository.DescriptionRepository;

import java.util.Optional;

public class DescriptionService {

    private final DescriptionRepository descriptionRepository;

    public DescriptionService(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    public Optional<Description> findByTag(String tag) {
        return descriptionRepository.findByTag(tag);
    }

    public Description save(Description description) {
        return descriptionRepository.save(description);
    }
}
