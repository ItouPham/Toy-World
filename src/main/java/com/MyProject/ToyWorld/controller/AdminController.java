package com.MyProject.ToyWorld.controller;

import com.MyProject.ToyWorld.dto.admin.AddNewProductDTO;
import com.MyProject.ToyWorld.dto.admin.AddNewUserDTO;
import com.MyProject.ToyWorld.dto.admin.EditProductDTO;
import com.MyProject.ToyWorld.dto.admin.EditUserDTO;
import com.MyProject.ToyWorld.entity.Product;
import com.MyProject.ToyWorld.entity.Role;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.service.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private AuthService authService;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CategoryService categoryService;
    private ProductService productService;
    private StorageService storageService;

    public AdminController(AdminService adminService, AuthService authService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, CategoryService categoryService, ProductService productService, StorageService storageService) {
        this.adminService = adminService;
        this.authService = authService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.categoryService = categoryService;
        this.productService = productService;
        this.storageService = storageService;
    }

    @GetMapping()
    public String showAdminHome() {
        return "admin-home";
    }

    @GetMapping("/product")
    public String showProductList(Model model) {
        model.addAttribute("products", productService.findAllProduct());
        return "product-management";
    }

    @GetMapping("/product/add")
    public String showAddProductPage(Model model) {
        model.addAttribute("product", new AddNewProductDTO());
        model.addAttribute("categories", categoryService.findAllCategory());
        return "add-new-product";
    }

    @PostMapping("/product/add")
    public String processAddNewProduct(@ModelAttribute("product") AddNewProductDTO addNewProductDTO,
                                       RedirectAttributes redirect) {

        adminService.addNewProduct(addNewProductDTO);
        redirect.addFlashAttribute("successMessage", "Add new product successfully");
        return "redirect:/admin/product";
    }

    @GetMapping("/product/update/{id}")
    public String showUpdateProductPage(@PathVariable("id") Long id, Model model) {

        Product product = productService.findProductById(id);
        EditProductDTO editProductDTO = new EditProductDTO();
        editProductDTO.setId(product.getId());
        editProductDTO.setProductName(product.getProductName());
        editProductDTO.setProductDescription(product.getProductDescription());
        editProductDTO.setPrice(product.getPrice());
        editProductDTO.setQuantity(product.getQuantity());
        editProductDTO.setCategoryID(product.getCategory().getId());
        editProductDTO.setProductImage(product.getProductImage());
        editProductDTO.setSize(product.getSize());

        model.addAttribute("categories", categoryService.findAllCategory());
        model.addAttribute("product", editProductDTO);
        return "update-product";
    }

    @PostMapping("/product/update")
    public String processUpdateProduct(@ModelAttribute("product") EditProductDTO editProductDTO,
                                       RedirectAttributes redirect) throws IOException{

        adminService.editProduct(editProductDTO);
        redirect.addFlashAttribute("successMessage", "Update product successfully");
        return "redirect:/admin/product";
    }

    @GetMapping("/product/delete/{id}")
    public String processDeleteProduct(@PathVariable Long id, RedirectAttributes redirect) throws IOException {
        adminService.deleteProduct(id);
        redirect.addFlashAttribute("successMessage", "Delete product successfully");
        return "redirect:/admin/product";
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
        editUserDTO.setPassword(user.getPassword());
        model.addAttribute("user", editUserDTO);
        model.addAttribute("roles", roleService.findAllRole());
        return "update-user";
    }

    @PostMapping("/user/update")
    public String processUpdateUser(@ModelAttribute("user") @Valid EditUserDTO editUserDTO,
                                    BindingResult result, RedirectAttributes redirect, Model model) {

        User account = authService.findById(editUserDTO.getId());

        if (editUserDTO.getPassword().equals("")) {
            editUserDTO.setPassword(account.getPassword());
        } else if (editUserDTO.getPassword().length() < 6) {
            result.addError(new FieldError("user", "password", "Password must be at least 6 characters"));
        }

        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAllRole());
            return "/update-user";
        }
        adminService.editUser(editUserDTO);
        redirect.addFlashAttribute("successMessage", "Save account successfully");
        return "redirect:/admin/user";
    }

    @GetMapping("/user/delete/{id}")
    public String processDeleteUser(@PathVariable String id, RedirectAttributes redirect) {
        adminService.deleteUser(id);
        redirect.addFlashAttribute("successMessage", "Delete user successfully");
        return "redirect:/admin/user";
    }
}
