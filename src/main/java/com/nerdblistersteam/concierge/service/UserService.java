package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.Role;
import com.nerdblistersteam.concierge.domain.User;
import com.nerdblistersteam.concierge.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
    private final MailService mailService;

    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
        encoder = new BCryptPasswordEncoder();
    }

    public User registerAdmin(User admin) {
        String secret = "{bcrypt}" + encoder.encode(admin.getPassword());
        admin.setPassword(secret);
        admin.setConfirmPassword(secret);
        admin.addRole(roleService.findByName("ROLE_ADMIN"));
        admin.setActivationCode(UUID.randomUUID().toString());
        admin.setEnabled(false);
        save(admin);
        sendEmail(admin);
        return admin;
    }

    public User inviteUser(User user) {
        user.addRole(roleService.findByName("ROLE_USER"));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setEnabled(true);
        save(user);
        return user;
    }

    public User registerUser(User user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setConfirmPassword(secret);
        user.addRole(roleService.findByName("ROLE_USER"));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setEnabled(false);
        save(user);
        return user;
    }

    public List<User> findByFirstName(String name) {
        return userRepository.findByFirstName(name);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void saveUsers(Set<User> users) {
        for (User user : users) {
            logger.info("Saving user: " + user.getEmail());
            userRepository.save(user);
        }
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void sendEmail(User user) {
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                mailService.sendActivationEmail(user);
            }
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email, activationCode);
    }
}
