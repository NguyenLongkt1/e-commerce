package com.example.userservice.controller;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import com.example.userservice.service.IUsersCommandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(name = "/command/users")
public class UsersCommandController {

    @Autowired
    IUsersCommandService usersCommandService;

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody UsersDTO dto){
        return ResponseEntity.ok(usersCommandService.doCreateUser(dto));
    }
}
