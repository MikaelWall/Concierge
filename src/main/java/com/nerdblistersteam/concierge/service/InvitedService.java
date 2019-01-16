package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.Invited;
import com.nerdblistersteam.concierge.repository.InvitedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class InvitedService {

    private final Logger logger = LoggerFactory.getLogger(InvitedService.class);
    private final InvitedRepository invitedRepository;
    private final MailService mailService;

    public InvitedService(InvitedRepository invitedRepository, MailService mailService) {
        this.invitedRepository = invitedRepository;
        this.mailService = mailService;
    }

    public Invited invite(String email, String firstName, String lastName, String addedByFullName, boolean isAdmin) {
        Invited invited = new Invited();
        invited.setFirstName(firstName);
        invited.setLastName(lastName);
        invited.setEmail(email);
        invited.setAddedByFullName(addedByFullName);
        invited.setAdmin(isAdmin);
        invited.setActivationCode(UUID.randomUUID().toString());
        save(invited);
        return invited;
    }

    public List<Invited> findAll() {
        return invitedRepository.findAll();
    }

    public Optional<Invited> findByEmail(String email) {
        return invitedRepository.findByEmail(email);
    }

    public Invited save(Invited invited) {
        return invitedRepository.save(invited);
    }

    @Transactional
    public void saveInvited(Set<Invited> inviteds) {
        for (Invited invited : inviteds) {
            logger.info("Saving invited: " + invited.getEmail());
            invitedRepository.save(invited);
        }
    }

    public void delete(Invited invited) {
        invitedRepository.delete(invited);
    }

    public void sendInvitationEmail(Invited invited) {
        mailService.sendInvitationEmail(invited);
    }

    public Optional<Invited> findByEmailAndActivationCode(String email, String activationCode) {
        return invitedRepository.findByEmailAndActivationCode(email, activationCode);
    }
}
