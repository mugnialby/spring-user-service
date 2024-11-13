package com.alby.spring_user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Long managerId;

    private String status;
}
