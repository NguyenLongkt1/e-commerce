package com.example.userservice.entity;

import com.example.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name="users",schema = "user-service")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseEntity {

    @Column(name="user_name",columnDefinition = "VARCHAR(20)")
    private String userName;

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

}
