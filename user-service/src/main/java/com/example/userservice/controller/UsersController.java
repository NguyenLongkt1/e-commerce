package com.example.userservice.controller;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import com.example.userservice.service.IUsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/api/users")
public class UsersController {

    @Autowired
    IUsersService usersService;

    @Autowired
    ObjectMapper mapper;

    @PostMapping
    public ResponseEntity<Users> createOrUpdateUser(@RequestPart(value="data") String dto, @RequestPart(value = "file",required = false) MultipartFile file) throws JsonProcessingException {
        UsersDTO userDto = mapper.readValue(dto,UsersDTO.class);
        return ResponseEntity.ok(usersService.doCreateOrUpdateUser(userDto,file));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> findUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(usersService.retrieve(userId));
    }

    @GetMapping("/find-by-username")
    public ResponseEntity<Users> findUserByUserName(@RequestParam String username) {
        return ResponseEntity.ok(usersService.findByUsername(username));
    }

    @GetMapping()
    public ResponseEntity<List<Users>> getAllUser() {
        return ResponseEntity.ok(usersService.getAllUser());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id){
        usersService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
