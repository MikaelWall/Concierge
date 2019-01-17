package com.nerdblistersteam.concierge.controller;

import com.nerdblistersteam.concierge.domain.Room;
import com.nerdblistersteam.concierge.domain.Schedule;
import com.nerdblistersteam.concierge.domain.Timespann;
import com.nerdblistersteam.concierge.domain.User;
import com.nerdblistersteam.concierge.service.RoomService;
import com.nerdblistersteam.concierge.service.ScheduleService;
import com.nerdblistersteam.concierge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLOutput;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Controller
public class ConciergeController {

    private final Logger logger = LoggerFactory.getLogger(ConciergeController.class);
    private RoomService roomService;
    private ScheduleService scheduleService;
    private UserService userService;
    //Detta är ett test för att kunna skilja rum i "room"
    private String rum = "Larsson";

    public ConciergeController(RoomService roomService, ScheduleService scheduleService, UserService userService) {
        this.roomService = roomService;
        this.scheduleService = scheduleService;
        this.userService = userService;
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
//    @GetMapping("/api/schedule")
//    public @ResponseBody
//   List<Schedule> getSchedule() {
//
//        System.out.println("Inuti getSchedule:");
//        List<Schedule> result = scheduleService.findAll();
//
//        System.out.println();
//        System.out.println("TEST UTSKRIFT AV getSchedule:");
//        System.out.println("Antal element i listan:" + result.size());
//        System.out.println();
//        System.out.println("Element 1: ");
//        System.out.println(result.get(0));
//        System.out.println();
//        System.out.println("Element 1 användaren:");
//        System.out.println(result.get(0).getUser());
//        System.out.println();
//        System.out.println("Hela listan: ");
//        System.out.println(result);
//        System.out.println();
//
//        return scheduleService.findAll();
//
//    }
    @GetMapping("/about")
    public String about() {
        return "Omoss";
    }


    @GetMapping("/allrooms")
    public String allrooms(Model model) {
        model.addAttribute("rooms", roomService.findAll()); 
        return "feed";
    }

    @GetMapping("/api/schedule")
    public @ResponseBody
    List<Schedule> getSchedule() {
        return scheduleService.findAll();
    }

    //Denna funktion tar ett värde från allrooms och visar sedan lediga och bokade tider för rummet.
    //Koden bör inte ligga här, kanske bättre placering i RoomService.
    @GetMapping("/{name}")
    public String room(@PathVariable String name,Model model) {

        //Bokningsfönstret per dag
        LocalDateTime openBooking = LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 0));
        LocalDateTime closeBooking = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0));

        List<Timespann> booked = new ArrayList<>();
        List<Timespann> free = new ArrayList<>();
        List<Timespann> freeAndBooked = new ArrayList<>();
        List<Schedule> schedulesFromDB = scheduleService.findAll();


        for (int i = 0; i < schedulesFromDB.size(); i++) {
            if (schedulesFromDB.get(i).getRoom().getName().equals(name) & schedulesFromDB.get(i).getStart().isAfter(openBooking)) {
                booked.add(new Timespann(schedulesFromDB.get(i).getStart(), schedulesFromDB.get(i).getStop(), true));
            }

        }

        booked.sort(Comparator.comparing(Timespann::getStart));

        for (int i = 0; i < booked.size(); i++) {
            if (i == 0) {
                if(booked.get(i).getStart().isAfter(openBooking)) {
                    freeAndBooked.add(new Timespann(openBooking, booked.get(i).getStart(), false));
                }

            }

            if ( i < (booked.size() -1)){
                if(booked.get(i).getStop().isEqual(booked.get(i+1).getStart())){
                    System.out.println("bokning är direkt efter");
                }

            }

            if ( i < (booked.size() -1)) {
                if(booked.get(i).getStop().isBefore(booked.get(i+1).getStart())) {
                    freeAndBooked.add(new Timespann(booked.get(i).getStop(), booked.get(i+1).getStart(), false));
                }

            }

            if (i == (booked.size()-1)) {
                if(booked.get(i).getStop().isBefore(closeBooking)) {
                    freeAndBooked.add(new Timespann(booked.get(i).getStop(), closeBooking, false));
                }

            }
        }

        for (int i = 0; i < booked.size(); i++) {
            freeAndBooked.add(booked.get(i));
        }

        freeAndBooked.sort(Comparator.comparing(Timespann::getStart));



        model.addAttribute( "times", free);
        model.addAttribute("bookings", freeAndBooked);
        return "Rum";
    }

    //Skapar bokning för ett specifikt rum och visar bokade och lediga tider. Just nu kan man bara boka den dag som dagens datum.
    //Här ska koppling göras till användare och rum, då det nu är hårdkodat.
    @PostMapping("/createbooking")
    public String createNewBooking(HttpServletRequest request, @RequestParam LocalTime start, @RequestParam LocalTime stop) {

        Timespann createdBooking = new Timespann(LocalDate.now().atTime(start), LocalDate.now().atTime(stop), true);
        User user1 = userService.findById(3L).get();
        Room room1 = roomService.findByName("Larsson").get();
        System.out.println(createdBooking.getStart());
        System.out.println(createdBooking.getStop());
        Schedule add = new Schedule(createdBooking.getStart(), createdBooking.getStop());
        System.out.println(add.getStart());
        System.out.println(add.getStop());
        add.addUser(user1);
        add.addRoom(room1);

        scheduleService.save(add);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
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
