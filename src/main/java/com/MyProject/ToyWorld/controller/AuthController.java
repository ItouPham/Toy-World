package com.MyProject.ToyWorld.controller;

import com.MyProject.ToyWorld.dto.RegisterDTO;
import com.MyProject.ToyWorld.service.AuthService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") @Valid RegisterDTO registerDTO,
                                  BindingResult result, RedirectAttributes redirect) {
        //Check if the email exist
        if(authService.isExistsByEmail(registerDTO.getEmail()))
            result.addError(new FieldError("user","email","Email address already in use"));


        //check if the passwords not match
        if (registerDTO.getPassword() != null && registerDTO.getConfirmPassword() != null) {
            if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
                result.addError(new FieldError("user", "password", "Password and Confirm password not match"));
            }
        }

        if (result.hasErrors()) {
            return "/register";
        }
        authService.register(registerDTO);
        redirect.addFlashAttribute("successMessage", "Register account successfully");
        return "redirect:/login";
    }
}
