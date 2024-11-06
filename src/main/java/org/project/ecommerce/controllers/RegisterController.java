package org.project.ecommerce.controllers;
import org.project.ecommerce.models.User;
import org.project.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService userService;

    RegisterController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }
    @PostMapping("/register")
    public String registerUser(@Validated User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "user/register";
        }
        userService.saveUser(user);
        return "redirect:/";
    }


}
