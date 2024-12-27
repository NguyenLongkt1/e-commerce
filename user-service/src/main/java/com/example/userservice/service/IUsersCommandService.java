package com.example.userservice.service;

import com.example.common.service.ICommandService;
import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;

public interface IUsersCommandService extends ICommandService<Users> {
    Users doCreateUser(UsersDTO dto);
    Users findByUsername(String username);
}
