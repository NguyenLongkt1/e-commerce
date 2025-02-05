package com.example.userservice.service;

import com.example.common.service.IQueryService;
import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;

import java.util.List;

public interface IUsersQueryService extends IQueryService<UsersDTO> {
    List<Users> getAllUser();
}
