package com.nerdblistersteam.concierge.controller;

import com.nerdblistersteam.concierge.domain.Invited;
import com.nerdblistersteam.concierge.domain.Role;
import com.nerdblistersteam.concierge.domain.User;
import com.nerdblistersteam.concierge.service.InvitedService;
import com.nerdblistersteam.concierge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private InvitedService invitedService;
    private UserService userService;

    public AuthController(InvitedService invitedService, UserService userService) {
        this.invitedService = invitedService;
        this.userService = userService;
    }

    private final Role adminRole = new Role("ROLE_ADMIN");
    private final Role userRole = new Role("ROLE_USER");

    @GetMapping("login")
    public String login() {
        return "auth/Loggin";
    }

    @GetMapping("/register")
    public String registrering() {
        return "/auth/Registrera";
    }

    @GetMapping("/fpassword")
    public String fPassword() {
        return "/auth/Glömtlösenord";
    }

    @GetMapping("/profile")
    public String profile() {
        return "/auth/ProfilSida";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // show validation errors
            logger.info("Validation errors were found while registering a new user.");
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/register";
        } else {
            // register new user
            boolean isAdmin = true;
            User newUser = userService.register(user, isAdmin);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/register";
        }
    }

    @GetMapping("/activate/{email}/{activationCode}")
    public String activateAdmin(@PathVariable String email, @PathVariable String activationCode) {
        Optional<User> user = userService.findByEmailAndActivationCode(email, activationCode);
        if (user.isPresent()) {
            User newUser = user.get();
            newUser.setEnabled(true);
            newUser.setConfirmPassword(newUser.getPassword());
            userService.save(newUser);
            return "auth/activated";
        }
        return "redirect:/";
    }

    @GetMapping("/register/{email}/{activationCode}")
    public String activateUserLink(Model model, @PathVariable String email, @PathVariable String activationCode) {
        Optional<Invited> invited = invitedService.findByEmailAndActivationCode(email, activationCode);
        if (invited.isPresent()) {
            Invited newInvited = invited.get();
            model
                    .addAttribute("email", email)
                    .addAttribute("firstName", newInvited.getFirstName())
                    .addAttribute("lastName", newInvited.getLastName())
                    .addAttribute("isAdmin", newInvited.isAdmin())
                    .addAttribute("readOnly", true);
            return "/auth/Registrera";
        }
        return "redirect:/";
    }

    @PostMapping("/register/{email}/{activationCode}")
    public String activateUser(@Valid User user, boolean isAdmin, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // show validation errors
            logger.info("Validation errors were found while registering a new user.");
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/register";
        } else {
            // register new user
            User newUser = userService.register(user, isAdmin);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/register";
        }
    }
}