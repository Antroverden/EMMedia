package com.effectivemobile.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String login;
    private String password;
}
