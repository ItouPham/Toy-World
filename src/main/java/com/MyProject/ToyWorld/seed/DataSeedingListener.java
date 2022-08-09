package com.MyProject.ToyWorld.seed;

import com.MyProject.ToyWorld.entity.Category;
import com.MyProject.ToyWorld.entity.Product;
import com.MyProject.ToyWorld.entity.Role;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.repository.CategoryRepository;
import com.MyProject.ToyWorld.repository.ProductRepository;
import com.MyProject.ToyWorld.repository.RoleRepository;
import com.MyProject.ToyWorld.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public DataSeedingListener(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        //Category
        if(!categoryRepository.findByCategoryName("KID TOYS").isPresent()){
            categoryRepository.save(new Category("KID TOYS"));
        }
        if(!categoryRepository.findByCategoryName("DOLLS").isPresent()){
            categoryRepository.save(new Category("DOLLS"));
        }
        if(!categoryRepository.findByCategoryName("KEY TOYS").isPresent()){
            categoryRepository.save(new Category("KEY TOYS"));
        }
        if(!categoryRepository.findByCategoryName("BOY TOYS").isPresent()){
            categoryRepository.save(new Category("BOY TOYS"));
        }

        //Product
        if(!productRepository.findByProductName("Product 1").isPresent()){
            productRepository.save(new Product("Product 1",3,new BigDecimal(25),12,"a1.jpg",categoryRepository.findById((long)1).get()));
        }
        if(!productRepository.findByProductName("Product 2").isPresent()){
            productRepository.save(new Product("Product 2",3,new BigDecimal(25),12,"a2.jpg",categoryRepository.findById((long)2).get()));
        }
        if(!productRepository.findByProductName("Product 3").isPresent()){
            productRepository.save(new Product("Product 3",3,new BigDecimal(25),12,"a3.jpg",categoryRepository.findById((long)3).get()));
        }
        if(!productRepository.findByProductName("Product 4").isPresent()){
            productRepository.save(new Product("Product 4",3,new BigDecimal(25),12,"a4.jpg",categoryRepository.findById((long)4).get()));
        }

        //Role
        if (!roleRepository.findByRoleName(com.MyProject.ToyWorld.constant.Role.ADMIN.name()).isPresent()) {
            roleRepository.save(new Role(UUID.randomUUID().toString(), "ADMIN"));
        }
        if (!roleRepository.findByRoleName(com.MyProject.ToyWorld.constant.Role.EMPLOYEE.name()).isPresent()) {
            roleRepository.save(new Role(UUID.randomUUID().toString(), "EMPLOYEE"));
        }
        if (!roleRepository.findByRoleName(com.MyProject.ToyWorld.constant.Role.CUSTOMER.name()).isPresent()) {
            roleRepository.save(new Role(UUID.randomUUID().toString(), "CUSTOMER"));
        }

        //Admin Account
        if (!userRepository.findByEmail("admin@gmail.com").isPresent()) {
            User admin = new User();
            admin.setId(UUID.randomUUID().toString());
            admin.setEmail("admin@gmail.com");
            String encodePassword = bCryptPasswordEncoder.encode("123456");
            admin.setPassword(encodePassword);
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName(com.MyProject.ToyWorld.constant.Role.ADMIN.name()).get());
            admin.setRoles(roles);
            admin.setAddress("Can Tho");
            admin.setFirstName("Cuong");
            admin.setLastName("Pham");
            admin.setTelephone("0123456789");
            userRepository.save(admin);
        }

        //Employee Account
        if (!userRepository.findByEmail("employee@gmail.com").isPresent()) {
            User employee = new User();
            employee.setId(UUID.randomUUID().toString());
            employee.setEmail("employee@gmail.com");
            String encodePassword = bCryptPasswordEncoder.encode("123456");
            employee.setPassword(encodePassword);
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName(com.MyProject.ToyWorld.constant.Role.EMPLOYEE.name()).get());
            employee.setRoles(roles);
            employee.setAddress("Can Tho");
            employee.setFirstName("Cuong");
            employee.setLastName("Pham");
            employee.setTelephone("0123456789");
            userRepository.save(employee);
        }

        //Customer Account
        if (!userRepository.findByEmail("customer@gmail.com").isPresent()) {
            User customer = new User();
            customer.setId(UUID.randomUUID().toString());
            customer.setEmail("customer@gmail.com");
            String encodePassword = bCryptPasswordEncoder.encode("123456");
            customer.setPassword(encodePassword);
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName(com.MyProject.ToyWorld.constant.Role.CUSTOMER.name()).get());
            customer.setRoles(roles);
            customer.setAddress("Can Tho");
            customer.setFirstName("Cuong");
            customer.setLastName("Pham");
            customer.setTelephone("0123456789");
            userRepository.save(customer);
        }
    }
}
