package com.example.apigateway.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UsersDTO {
    private Long id;
    private String userName;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;
    private Integer status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long createdBy;
    private Long updateBy;
}
