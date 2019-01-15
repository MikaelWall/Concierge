package com.nerdblistersteam.concierge.bootstrap;

import com.nerdblistersteam.concierge.domain.Role;
import com.nerdblistersteam.concierge.domain.Room;
import com.nerdblistersteam.concierge.domain.User;
import com.nerdblistersteam.concierge.repository.RoleRepository;
import com.nerdblistersteam.concierge.repository.RoomRepository;
import com.nerdblistersteam.concierge.repository.UserRepository;
import com.nerdblistersteam.concierge.service.MailService;
import com.nerdblistersteam.concierge.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RoomRepository roomRepository;
    private UserService userService;

    private Map<String, User> users = new HashMap<>();
    private List<Room> rooms = new ArrayList<>();

    public DatabaseLoader(UserRepository userRepository, RoleRepository roleRepository, RoomRepository roomRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roomRepository = roomRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

        addUsersAndRoles();
        addRooms();

    }

    private void addUsersAndRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        User user = new User("user@gmail.com", secret, true, "Henrik", "Johansson");
        user.addRole(userRole);
        user.setConfirmPassword(secret);
        user.setAddedByFullName("Te Hung Tseng");
        userRepository.save(user);
        users.put("user@gmail.com", user);
        userService.sendEmail(user);

        User admin = new User("admin@gmail.com", secret, true, "Te Hung", "Tseng");
        admin.addRole(adminRole);
        admin.setConfirmPassword(secret);
        userRepository.save(admin);
        users.put("admin@gmail.com", admin);
        userService.sendEmail(admin);

        User user2 = new User("user2@gmail.com", secret, true, "Björn", "Persson");
        user2.addRole(userRole);
        user2.setConfirmPassword(secret);
        user2.setAddedByFullName("Te Hung Tseng");
        userRepository.save(user2);
        users.put("user2@gmail.com", user2);
    }

    private void addRooms() {
        Room larsson = new Room("Larsson");
        roomRepository.save(larsson);
        rooms.add(larsson);

        Room heden = new Room("Hedén");
        roomRepository.save(heden);
        rooms.add(heden);

        Room micael = new Room("Micael");
        roomRepository.save(micael);
        rooms.add(micael);

        Room lovelace = new Room("Lovelace");
        roomRepository.save(lovelace);
        rooms.add(lovelace);
    }
}