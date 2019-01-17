package com.nerdblistersteam.concierge.controller;

import com.nerdblistersteam.concierge.domain.Invited;
import com.nerdblistersteam.concierge.domain.User;
import com.nerdblistersteam.concierge.service.InvitedService;
import com.nerdblistersteam.concierge.service.UserService;
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
public class InviteController {

    private final UserService userService;
    private final InvitedService invitedService;

    public InviteController(UserService userService, InvitedService invitedService) {
        this.userService = userService;
        this.invitedService = invitedService;
    }

    @GetMapping("/invite")
    public String invite(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        model.addAttribute("addedByFullName", ((User)authentication.getPrincipal()).getFullName());
        Optional<User> currentUserFind = userService.findByEmail("admin@gmail.com");
        currentUserFind.ifPresent(user -> model.addAttribute("user", user));
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
    public String deleteInvite(@RequestParam String email, RedirectAttributes redirectAttributes) {
        Optional<Invited> invitedDeleteFind = invitedService.findByEmail(email);
        if (invitedDeleteFind.isPresent()) {
            Invited invitedDelete = invitedDeleteFind.get();
            invitedService.delete(invitedDelete);
            redirectAttributes.addFlashAttribute("deleted", true);
        } else {
            redirectAttributes.addFlashAttribute("error", true);
        }
        return "redirect:/invite";
    }

    @PostMapping("/sendInvite")
    public String sendInvite(RedirectAttributes redirectAttributes, Model model) {
        List<Invited> invitedList = invitedService.findAll();
        if (!invitedList.isEmpty()) {
            for (Invited invited : invitedList) {
                invitedService.sendInvitationEmail(invited);
            }
            redirectAttributes.addFlashAttribute("success", true);
        } else {
            redirectAttributes.addFlashAttribute("empty", true);
        }
        return "redirect:/invite";
    }
}
