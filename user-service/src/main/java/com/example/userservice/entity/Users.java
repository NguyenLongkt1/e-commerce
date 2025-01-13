package com.example.userservice.entity;

import com.example.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table(name="users",schema = "user-service")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseEntity {

    @Column(name="user_name",columnDefinition = "VARCHAR(20)")
    private String username;

    @Column(name="full_name",columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name="phone_number",columnDefinition = "VARCHAR(20)")
    private String phoneNumber;

    @Column(name="email",columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name="address",columnDefinition = "TEXT")
    private String address;

    @Column(name="password",columnDefinition = "VARCHAR(500)")
    private String password;

    @Column(name="status",columnDefinition = "INT(1)")
    private Integer status;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
