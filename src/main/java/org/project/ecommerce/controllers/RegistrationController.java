package org.project.ecommerce.controllers;

import org.project.ecommerce.models.RegistrationForm;
import org.project.ecommerce.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService registrationService;

    RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }
    @GetMapping
    public String getRegistrationForm(Model model){
        model.addAttribute("registrationForm", new RegistrationForm());
        return "user/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("registrationForm") RegistrationForm form){
        registrationService.registerUser(form.getUsername(), form.getPassword(), "ROLE_CUSTOMER");
        return "redirect:/login";
    }



}
