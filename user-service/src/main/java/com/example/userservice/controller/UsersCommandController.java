package com.example.userservice.controller;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import com.example.userservice.service.IUsersCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/command/users")
public class UsersCommandController {

    @Autowired
    IUsersCommandService usersCommandService;

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody UsersDTO dto){
        return ResponseEntity.ok(usersCommandService.doCreateUser(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> findUserById(@PathVariable Long userId){
        return ResponseEntity.ok(usersCommandService.retrieve(userId));
    }

    @GetMapping("/find-by-username")
    public ResponseEntity<Users> findUserByUserName(@RequestParam String username){
        return ResponseEntity.ok(usersCommandService.findByUsername(username));
    }
}
