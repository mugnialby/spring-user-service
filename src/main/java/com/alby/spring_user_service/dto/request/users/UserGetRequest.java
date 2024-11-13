package com.alby.spring_user_service.dto.request.users;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGetRequest {
    
    @NotNull
    @Digits(integer = 12, fraction = 0)
    private Long userId;
}
