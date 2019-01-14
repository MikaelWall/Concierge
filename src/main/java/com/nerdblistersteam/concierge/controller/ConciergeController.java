package com.nerdblistersteam.concierge.controller;

import com.nerdblistersteam.concierge.domain.Description;
import com.nerdblistersteam.concierge.domain.Room;
import com.nerdblistersteam.concierge.repository.DescriptionRepository;
import com.nerdblistersteam.concierge.repository.RoomRepository;
import com.nerdblistersteam.concierge.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ConciergeController {


    private final Logger logger = LoggerFactory.getLogger(ConciergeController.class);
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DescriptionRepository descriptionRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/createroom")
    public String createroom() {
        return "createroom";
    }

    @PostMapping("/createroom")
    public String createNewRoom(@RequestParam String name, @RequestParam int seats, @RequestParam String hdmi, @RequestParam String whiteboard) {
        Room newRoom = new Room(name, seats);
        //Description newDescription = new Description(seats);
        roomRepository.save(newRoom);
        // descriptionRepository.save(newDescription);
        System.out.println("Skapat rum " + name);
        return "index";
    }
}
