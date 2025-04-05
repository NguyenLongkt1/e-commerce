package com.example.apigateway.service;

import com.example.apigateway.dto.response.UserDetail;
import com.example.apigateway.dto.response.UsersDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Value("${user-service.host}")
    private String userServiceUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return objectMapper.convertValue(callApiGetUserByUserName(username), UserDetail.class);
    }

    UsersDTO callApiGetUserByUserName(String username) {

        String url = userServiceUrl + "/api/users/find-by-username";
        String urlWithParams = UriComponentsBuilder.fromUri(URI.create(url))
                .queryParam("username", username)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<UsersDTO> response = restTemplate.exchange(
                urlWithParams,
                HttpMethod.GET,
                requestEntity,
                UsersDTO.class
        );
        return response.getBody();
    }
}
