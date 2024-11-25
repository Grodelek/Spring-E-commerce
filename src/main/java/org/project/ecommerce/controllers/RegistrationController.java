package org.project.ecommerce.controllers;

import org.project.ecommerce.models.User;
import org.project.ecommerce.service.RegistrationService;
import org.project.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final UserService userService;

    RegistrationController(RegistrationService registrationService, UserService userService){
        this.registrationService = registrationService;
        this.userService = userService;
    }
    @GetMapping
    public String getRegistrationForm(Model model){
        model.addAttribute("registrationForm", new User());
        return "user/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("registrationForm") User userForm){
        registrationService.registerUser(userForm.getUsername(), userForm.getPassword(),userForm.getRoles());
        return "redirect:/login";
    }
}
