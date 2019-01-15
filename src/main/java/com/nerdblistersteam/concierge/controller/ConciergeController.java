package com.nerdblistersteam.concierge.controller;

import com.nerdblistersteam.concierge.domain.Room;
import com.nerdblistersteam.concierge.service.RoomService;
import com.nerdblistersteam.concierge.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ConciergeController {

    private final Logger logger = LoggerFactory.getLogger(ConciergeController.class);
    private RoomService roomService;
    private ScheduleService scheduleService;

    public ConciergeController(RoomService roomService, ScheduleService scheduleService) {
        this.roomService = roomService;
        this.scheduleService = scheduleService;
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

    @GetMapping("/allrooms")
    public String allrooms() {
        return "feed";
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



}
