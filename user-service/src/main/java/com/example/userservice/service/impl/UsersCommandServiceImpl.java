package com.example.userservice.service.impl;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Role;
import com.example.userservice.entity.Users;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UsersRepository;
import com.example.userservice.service.IUsersCommandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@Service
public class UsersCommandServiceImpl implements IUsersCommandService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.user.defaultPassword}")
    String defaultPassword;

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
    public Users doCreateUser(UsersDTO dto, MultipartFile file) {
        Users user = modelMapper.map(dto, Users.class);

        if (findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("User already existed");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = dto.getPassword();
        if(!StringUtils.hasLength(password)){
            password = defaultPassword;
        }
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = roleRepository.findByRoleCodeIn(dto.getRoles());
        user.setRoles(roles);

        if(!file.isEmpty()){
//           String path = doUploadFile(file);
//           user.setAvatar(path);
        }

        return create(user);
    }

    String doUploadFile(MultipartFile file) {

        String url = "http://localhost:8084/files/storage/upload";
        String urlWithParams = UriComponentsBuilder.fromUri(URI.create(url))
                .queryParam("file", file)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                urlWithParams,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return response.getBody();
    }

    @Override
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username).orElse(null);
    }
}
