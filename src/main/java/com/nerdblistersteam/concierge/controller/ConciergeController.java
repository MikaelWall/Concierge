package com.nerdblistersteam.concierge.controller;

import com.nerdblistersteam.concierge.domain.Schedule;
import com.nerdblistersteam.concierge.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ConciergeController {

    private ScheduleService scheduleService;

    //Detta är ett test för att kunna skilja rum i "room"
    private String rum = "Larsson";

    public ConciergeController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/api/schedule")
    public @ResponseBody
    List<Schedule> getSchedule() {
        return scheduleService.findAll();
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
}
