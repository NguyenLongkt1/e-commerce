package com.example.userservice.controller;

import com.example.userservice.entity.Users;
import com.example.userservice.service.IUsersQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/query/users")
public class UsersQueryController {
    @Autowired
    IUsersQueryService usersQueryService;

    @GetMapping()
    public ResponseEntity<List<Users>> getAllUser() {
        return ResponseEntity.ok(usersQueryService.getAllUser());
    }
}
