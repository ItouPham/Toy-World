package com.MyProject.ToyWorld.controller;

import com.MyProject.ToyWorld.dto.RegisterDTO;
import com.MyProject.ToyWorld.dto.admin.EditUserDTO;
import com.MyProject.ToyWorld.entity.Role;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.security.CustomUserDetails;
import com.MyProject.ToyWorld.service.AdminService;
import com.MyProject.ToyWorld.service.AuthService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {
    private AuthService authService;
    private AdminService adminService;

    public AuthController(AuthService authService, AdminService adminService) {
        this.authService = authService;
        this.adminService = adminService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/login")
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
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

    @GetMapping("/profile")
    public String showProfilePage(@AuthenticationPrincipal CustomUserDetails loggedUser,
                                  Model model){
        model.addAttribute("user",authService.findUserByEmail(loggedUser.getUsername()));
        return "profile";
    }

    @GetMapping("/profile/update")
    public String showUpdateProfilePage(@AuthenticationPrincipal CustomUserDetails loggedUser,
                                        Model model){
        User user = authService.findUserByEmail(loggedUser.getUsername());
        EditUserDTO editUserDTO = new EditUserDTO();
        editUserDTO.setId(user.getId());
        editUserDTO.setEmail(user.getEmail());
        editUserDTO.setFirstName(user.getFirstName());
        editUserDTO.setLastName(user.getLastName());
        editUserDTO.setTelephone(user.getTelephone());
        editUserDTO.setAddress(user.getAddress());
        model.addAttribute("user",editUserDTO);
        return "update-profile";
    }

//    @PostMapping("/profile/update")
//    public String processUpdateProfile(@ModelAttribute("user") @Valid EditUserDTO editUserDTO,
//                                       RedirectAttributes redirect, CustomUserDetails loggedUser,
//                                       BindingResult result, Model model){
//        if(editUserDTO.getPassword() == ""){
//            editUserDTO.setPassword(loggedUser.getPassword());
//        }else if(editUserDTO.getPassword().length() < 6){
//            result.addError(new FieldError("user", "password", "Password must be at least 6 characters"));
//        }
//        User user = authService.findUserByEmail(loggedUser.getUsername());
//        editUserDTO.setRoleID(user.getRoles().stream().map(Role::getId).findFirst().orElseThrow(() -> new RuntimeException("Role Not Found")));
//
//        if (result.hasErrors()) {
//            return "/update-profile";
//        }
//
//        adminService.editUser(editUserDTO);
//        redirect.addFlashAttribute("successMessage", "Update profile successfully");
//        return "redirect:/profile";
//    }
}
