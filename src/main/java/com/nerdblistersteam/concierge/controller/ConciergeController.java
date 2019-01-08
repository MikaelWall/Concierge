package com.nerdblistersteam.concierge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConciergeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
