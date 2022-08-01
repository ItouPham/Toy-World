package com.MyProject.ToyWorld.dto.admin;

import javax.validation.constraints.NotBlank;

public class EditUserDTO {
    private String id;

    private String email;

    private String password;

    @NotBlank(message = "First name can not empty")
    private String firstName;

    @NotBlank(message = "Last name can not empty")
    private String lastName;

    private String address;

    private String telephone;

    private String roleID;

    public EditUserDTO() {
    }

    public EditUserDTO(String id, String email, String password, String firstName, String lastName, String address, String telephone, String roleID) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.roleID = roleID;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
}
