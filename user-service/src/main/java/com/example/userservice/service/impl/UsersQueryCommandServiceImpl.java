package com.example.userservice.service.impl;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import com.example.userservice.repository.UsersRepository;
import com.example.userservice.service.IUsersQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersQueryCommandServiceImpl implements IUsersQueryService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Page<UsersDTO> getPaginationItem(Pageable pageable, UsersDTO searchModel) {
        return null;
    }

    @Override
    public List<Users> getAllUser() {
        return usersRepository.findAll();
    }
}
