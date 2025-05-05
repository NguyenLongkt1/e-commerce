package com.example.userservice.service.impl;

import com.example.common.exception.BussinessException;
import com.example.userservice.dto.CartDTO;
import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UsersRepository;
import com.example.userservice.service.IUsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements IUsersService {

    @Value("${file-service.host}")
    private String fileServiceUrl;

    @Value("${cart-service.host}")
    private String cartServiceUrl;

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
        Users user = retrieve(id);
        if (!ObjectUtils.isEmpty(user)) {
            user.setDelete(true);
            update(user);
        }
    }

    @Override
    @Transactional
    public Users doCreateOrUpdateUser(UsersDTO dto, MultipartFile file) {
        Users user = modelMapper.map(dto, Users.class);
        if (ObjectUtils.isEmpty(dto.getId())) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = dto.getPassword();
            if (!StringUtils.hasLength(password)) {
                password = defaultPassword;
            }
            user.setPassword(passwordEncoder.encode(password));

//            Set<Role> roles = roleRepository.findByRoleCodeIn(dto.getRoles());
//            user.setRoles(roles);
            user.setDelete(false);

            if (!ObjectUtils.isEmpty(file)) {
                String path = doUploadFile(file);
                user.setAvatar(path);
            }

            Users users = create(user);
            createCart(users.getId());
            return users;
        } else {
            Users oldUser = retrieve(dto.getId());
            if (ObjectUtils.isEmpty(oldUser)) {
                throw new RuntimeException("Not found user with id: " + dto.getId());
            }
            oldUser.setFullName(user.getFullName());
            oldUser.setUsername(user.getUsername());
            oldUser.setPhoneNumber(user.getPhoneNumber());
            oldUser.setEmail(user.getEmail());
            oldUser.setAddress(user.getAddress());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setGender(user.getGender());
            oldUser.setStatus(user.getStatus());
            if (!ObjectUtils.isEmpty(file)) {
                String path = doUploadFile(file);
                oldUser.setAvatar(path);
            }
            update(oldUser);
            return oldUser;
        }
    }

    String doUploadFile(MultipartFile file) {

        String url = fileServiceUrl + "/storage/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(file.getResource(), headers));

        // Tạo request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
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

    @Override
    public List<Users> getAllUser() {
        return usersRepository.findAll();
    }

    UsersDTO mappingToDTO(Users user) {
        return UsersDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .status(user.getStatus())
                .build();
    }

    @Override
    public UsersDTO getUserInfo(Long id) {
        Users user = retrieve(id);
        UsersDTO dto = null;
        if (!ObjectUtils.isEmpty(user)) {
            dto = mappingToDTO(user);
            CartDTO cartDTO = callApiGetCart(user.getId());
            if (!ObjectUtils.isEmpty(cartDTO)) {
                dto.setCartInfo(cartDTO);
            }
        }

        return dto;
    }

    CartDTO callApiGetCart(Long userId) {

        String url = cartServiceUrl + "/api/carts/cart-by-user";
        String urlWithParams = UriComponentsBuilder.fromUri(URI.create(url))
                .queryParam("userId", userId)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<CartDTO> response = restTemplate.exchange(
                urlWithParams,
                HttpMethod.GET,
                requestEntity,
                CartDTO.class
        );
        return response.getBody();
    }

    public void createCart(Long userId) {

        String url = cartServiceUrl + "/api/carts";

        Map<String, Object> body = new HashMap<>();
        body.put("userId", userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Tạo request entity
        HttpEntity<?> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );
        if (response.getStatusCode() != HttpStatus.OK) throw new RuntimeException("Đã có lỗi xảy ra khi tạo user");
    }
}
