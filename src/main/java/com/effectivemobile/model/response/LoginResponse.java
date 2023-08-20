package com.effectivemobile.model.response;

import lombok.Data;
import lombok.experimental.Accessors;
import com.effectivemobile.entity.enums.RoleEnum;

@Data
@Accessors(chain = true)
public class LoginResponse {
    private Long userId;
    private String username;
    private String token;
    private RoleEnum role;
}
