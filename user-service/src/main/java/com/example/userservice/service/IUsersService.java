package com.example.userservice.service;

import com.example.common.exception.BussinessException;
import com.example.common.service.ICommandService;
import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUsersService extends ICommandService<Users> {
    Users doCreateOrUpdateUser(UsersDTO dto, MultipartFile file);

    Users findByUsername(String username);

    List<Users> getAllUser();
}
