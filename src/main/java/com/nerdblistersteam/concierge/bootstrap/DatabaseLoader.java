package com.nerdblistersteam.concierge.bootstrap;

import com.nerdblistersteam.concierge.domain.*;
import com.nerdblistersteam.concierge.repository.DescriptionRepository;
import com.nerdblistersteam.concierge.repository.RoleRepository;
import com.nerdblistersteam.concierge.repository.RoomRepository;
import com.nerdblistersteam.concierge.repository.UserRepository;
import com.nerdblistersteam.concierge.service.MailService;
import com.nerdblistersteam.concierge.service.RoomService;
import com.nerdblistersteam.concierge.service.ScheduleService;
import com.nerdblistersteam.concierge.service.UserService;
import javassist.runtime.Desc;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RoomRepository roomRepository;
    private DescriptionRepository descriptionRepository;
    private RoomService roomService;
    private UserService userService;
    private ScheduleService scheduleService;

    private Map<String, User> users = new HashMap<>();
    private List<Room> rooms = new ArrayList<>();
    private Set<Description> descriptions = new HashSet<>();

    public DatabaseLoader(UserRepository userRepository, RoleRepository roleRepository, RoomRepository roomRepository, DescriptionRepository descriptionRepository, RoomService roomService, UserService userService, ScheduleService scheduleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roomRepository = roomRepository;
        this.descriptionRepository = descriptionRepository;
        this.roomService = roomService;
        this.userService = userService;
        this.scheduleService = scheduleService;
    }

    @Override
    public void run(String... args) {

        addUsersAndRoles();
        addRoomsAndDescriptions();
        addSchedules();
        removeSchedule();

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
        userService.sendEmail(user2);
    }

    private void addRoomsAndDescriptions() {

        Description hdmi = new Description("HDMI");
        descriptionRepository.save(hdmi);
        descriptions.add(hdmi);
        Description whiteboard = new Description("Whiteboard");
        descriptionRepository.save(whiteboard);
        descriptions.add(whiteboard);
        Description projector = new Description("Projector");
        descriptionRepository.save(projector);
        descriptions.add(projector);

        Room larsson = new Room("Larsson", 20);
        larsson.addDescriptions(descriptions);
        roomRepository.save(larsson);
        rooms.add(larsson);

        Room heden = new Room("Hedén", 21);
        heden.addDescriptions(descriptions);
        roomRepository.save(heden);
        rooms.add(heden);

        Room micael = new Room("Micael", 20);
        micael.addDescriptions(descriptions);
        roomRepository.save(micael);
        rooms.add(micael);

        Room lovelace = new Room("Lovelace", 8);
        lovelace.addDescriptions(descriptions);
        roomRepository.save(lovelace);
        rooms.add(lovelace);
    }

    private void addSchedules() {
        User bookedByUser = userService.findById(3L).get();
        Room bookedRoom = roomService.findByName("Larsson").get();
        Schedule schedule1 = new Schedule(LocalDateTime.now(), LocalDateTime.of(2019,1,20,20, 0));
        schedule1.addUser(bookedByUser);
        schedule1.addRoom(bookedRoom);
        scheduleService.save(schedule1);
    }

    private void removeSchedule() {
        List<Schedule> schedules = scheduleService.findByStop(LocalDateTime.of(2019, 1, 20, 20, 0));
        if (!schedules.isEmpty()) {
            scheduleService.delete(schedules.get(0));
        }
    }
}