package com.nerdblistersteam.concierge.controller;

import com.nerdblistersteam.concierge.domain.Invited;
import com.nerdblistersteam.concierge.domain.Room;
import com.nerdblistersteam.concierge.domain.User;
import com.nerdblistersteam.concierge.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ConciergeController {

    private final Logger logger = LoggerFactory.getLogger(ConciergeController.class);
    private RoomService roomService;
    private ScheduleService scheduleService;
    private UserService userService;
    private InvitedService invitedService;
    private RoleService roleService;


    public ConciergeController(RoomService roomService, ScheduleService scheduleService, UserService userService, InvitedService invitedService, RoleService roleService) {
        this.roomService = roomService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.invitedService = invitedService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/createroom")
    public String createroom(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "createroom";
    }

    @GetMapping("/calendar")
    public String calendar(Model model) {
        model.addAttribute("bookings", scheduleService.findAll());
        return "calendar";
    }

    @GetMapping("/about")
    public String about() {
        return "Omoss";
    }

    @PostMapping("/createroom")
    public String createNewRoom(@RequestParam String name, @RequestParam int seats) {
        Room newRoom = new Room(name, seats);
        //Description newDescription = new Description(seats);
        roomService.save(newRoom);
       // descriptionRepository.save(newDescription);
        System.out.println("Skapat rum " + name);
        return "index";
    }

    @PostMapping("/deleteroom")
    public String deleteRoom(@RequestParam String deleteName, Model model) {
        Optional<Room> findDelRoom = roomService.findByName(deleteName);
        if (findDelRoom.isPresent()) {
            Room delRoom = findDelRoom.get();
            roomService.delete(delRoom);
            model.addAttribute("success", model.containsAttribute("success"));
        } else {
            model.addAttribute("danger", model.containsAttribute("danger"));
        }

        return "redirect:/createroom";
    }

//    @Secured({"ROLE_ADMIN"})
    @GetMapping("/invite")
    public String invite(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        model.addAttribute("addedByFullName", ((User)authentication.getPrincipal()).getFullName());
        Optional<User> currentUserFind = userService.findByEmail("admin@gmail.com");
        if (currentUserFind.isPresent()) {
            model.addAttribute("user", currentUserFind.get());
        }
        model.addAttribute("invited", invitedService.findAll());
//        System.out.println(((User)authentication.getPrincipal()).getFullName());
        return "invite";
    }

    @PostMapping("/addInvite")
    public String addInvite(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String addedByFullName, @RequestParam boolean isAdmin, RedirectAttributes redirectAttributes) {
        invitedService.invite(email, firstName, lastName, addedByFullName, isAdmin);
        return "redirect:/invite";
    }

    @PostMapping("/deleteInvite")
    public String deleteInvite(@RequestParam String email, Model model) {
        Optional<Invited> invitedDeleteFind = invitedService.findByEmail(email);
        if (invitedDeleteFind.isPresent()) {
            Invited invitedDelete = invitedDeleteFind.get();
            invitedService.delete(invitedDelete);
            model.addAttribute("success", model.containsAttribute("success"));
        } else {
            model.addAttribute("danger", model.containsAttribute("danger"));
        }
        return "redirect:/invite";
    }

    @PostMapping("/sendInvite")
    public String sendInvite(RedirectAttributes redirectAttributes, Model model) {
        List<Invited> invitedList = invitedService.findAll();
        for (Invited invited : invitedList) {
            invitedService.sendInvitationEmail(invited);
        }
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/invite";
    }

}
