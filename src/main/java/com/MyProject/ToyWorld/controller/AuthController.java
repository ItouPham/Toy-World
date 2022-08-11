package com.MyProject.ToyWorld.controller;

import com.MyProject.ToyWorld.dto.EditProfileDTO;
import com.MyProject.ToyWorld.dto.RegisterDTO;
import com.MyProject.ToyWorld.dto.admin.EditUserDTO;
import com.MyProject.ToyWorld.entity.Role;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.security.CustomUserDetails;
import com.MyProject.ToyWorld.service.AdminService;
import com.MyProject.ToyWorld.service.AuthService;
import com.MyProject.ToyWorld.service.RoleService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(AuthService authService, AdminService adminService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authService = authService;
        this.adminService = adminService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        EditProfileDTO editProfileDTO = new EditProfileDTO();
        editProfileDTO.setId(user.getId());
        editProfileDTO.setEmail(user.getEmail());
        editProfileDTO.setFirstName(user.getFirstName());
        editProfileDTO.setLastName(user.getLastName());
        editProfileDTO.setTelephone(user.getTelephone());
        editProfileDTO.setAddress(user.getAddress());
        editProfileDTO.setPassword(loggedUser.getPassword());
        model.addAttribute("user",editProfileDTO);
        model.addAttribute("roles",roleService.findAllRole());
        return "update-profile";
    }

    @PostMapping("profile/update")
    public String processUpdateProfile(@ModelAttribute("user") @Valid EditProfileDTO editProfileDTO,
                                       BindingResult result, RedirectAttributes redirect, Model model){

        User user = authService.findById(editProfileDTO.getId());

        if((editProfileDTO.getPassword() == null) || (editProfileDTO.getPassword().equals(""))){
            editProfileDTO.setPassword(user.getPassword());
        }else if(editProfileDTO.getPassword().length() < 6){
            result.addError(new FieldError("user","password", "Password must be at least 6 characters"));
        } else{
            editProfileDTO.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        if (result.hasErrors()) {
            model.addAttribute("roles",roleService.findAllRole());
            return "/update-profile";
        }

        editProfileDTO.setRoleID(user.getRoles().stream().map(Role::getId).findFirst().orElseThrow(() -> new RuntimeException("Role Not Found")));
        authService.editProfile(editProfileDTO);
        redirect.addFlashAttribute("successMessage", "Save profile successfully");
        return "redirect:/profile";
    }
}
