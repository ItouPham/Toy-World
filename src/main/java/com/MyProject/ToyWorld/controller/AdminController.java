package com.MyProject.ToyWorld.controller;

import com.MyProject.ToyWorld.dto.admin.AddNewUserDTO;
import com.MyProject.ToyWorld.dto.admin.EditUserDTO;
import com.MyProject.ToyWorld.entity.Role;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.service.AdminService;
import com.MyProject.ToyWorld.service.AuthService;
import com.MyProject.ToyWorld.service.RoleService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private AuthService authService;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminController(AdminService adminService, AuthService authService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminService = adminService;
        this.authService = authService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping()
    public String showAdminHome() {
        return "admin-home";
    }

    @GetMapping("/product")
    public String showProductList() {
        return "product-management";
    }

    @GetMapping("/product/add")
    public String showAddProductPage() {
        return "add-new-product";
    }

    @GetMapping("/product/update")
    public String showUpdateProductPage() {
        return "update-product";
    }

    @GetMapping("/user")
    public String showUserList(Model model) {
        model.addAttribute("users", authService.findAll());
        return "user-management";
    }

    @GetMapping("/user/add")
    public String showAddUserPage(Model model) {
        model.addAttribute("user", new AddNewUserDTO());
        model.addAttribute("roles", roleService.findAllRole());
        return "add-new-user";
    }

    @PostMapping("/user/add")
    public String processAddNewUser(@ModelAttribute("user") @Valid AddNewUserDTO addNewUserDTO,
                                    BindingResult result, RedirectAttributes redirect, Model model) {
        //Check if the email exist
        if (authService.isExistsByEmail(addNewUserDTO.getEmail()))
            result.addError(new FieldError("user", "email", "Email address already in use"));


        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAllRole());
            return "/add-new-user";
        }

        adminService.addNewUser(addNewUserDTO);
        redirect.addFlashAttribute("successMessage", "Save account successfully");
        return "redirect:/admin/user";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateUserPage(@PathVariable("id") String id, Model model) {
        User user = authService.findById(id);
        EditUserDTO editUserDTO = new EditUserDTO();
        editUserDTO.setId(user.getId());
        editUserDTO.setEmail(user.getEmail());
        editUserDTO.setFirstName(user.getFirstName());
        editUserDTO.setLastName(user.getLastName());
        editUserDTO.setAddress(user.getAddress());
        editUserDTO.setTelephone(user.getTelephone());
        editUserDTO.setRoleID(user.getRoles().stream().map(Role::getId).findFirst().orElseThrow(() -> new RuntimeException("Role Not Found")));
        model.addAttribute("user",editUserDTO);
        model.addAttribute("roles",roleService.findAllRole());
        return "update-user";
    }

    @PostMapping("/user/update")
    public String processUpdateUser(@ModelAttribute("user") @Valid EditUserDTO editUserDTO,
                                    BindingResult result, RedirectAttributes redirect, Model model){

        if(result.hasErrors()){
            model.addAttribute("roles",roleService.findAllRole());
            return "/update-user";
        }
        adminService.editUser(editUserDTO);
        redirect.addFlashAttribute("successMessage", "Save account successfully");
        return "redirect:/admin/user";
    }

    @GetMapping("/user/delete/{id}")
    public String processDeleteUser(@PathVariable String id, RedirectAttributes redirect){
        adminService.deleteUser(id);
        redirect.addFlashAttribute("successMessage", "Delete user successfully");
        return"redirect:/admin/user";
    }
}
