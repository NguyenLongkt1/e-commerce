package com.example.userservice.service.impl;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import com.example.userservice.repository.UsersRepository;
import com.example.userservice.service.IUsersCommandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersCommandServiceImpl implements IUsersCommandService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Users create(Users entity) {
        return usersRepository.save(entity);
    }

    @Override
    public Users retrieve(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Users entity) {
        usersRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public Users doCreateUser(UsersDTO dto) {
        Users user = modelMapper.map(dto,Users.class);

        if (findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("User already existed");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return create(user);
    }

    @Override
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username).orElse(null);
    }
}
