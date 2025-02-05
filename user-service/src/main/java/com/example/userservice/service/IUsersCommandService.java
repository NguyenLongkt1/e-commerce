package com.example.userservice.service;

import com.example.common.service.ICommandService;
import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import org.springframework.web.multipart.MultipartFile;

public interface IUsersCommandService extends ICommandService<Users> {
    Users doCreateUser(UsersDTO dto, MultipartFile file);

    Users findByUsername(String username);
}
