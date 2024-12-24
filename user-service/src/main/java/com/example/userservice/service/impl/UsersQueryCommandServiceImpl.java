package com.example.userservice.service.impl;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.service.IUsersQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsersQueryCommandServiceImpl implements IUsersQueryService {

    @Override
    public Page<UsersDTO> getPaginationItem(Pageable pageable, UsersDTO searchModel) {
        return null;
    }
}
