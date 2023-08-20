package com.effectivemobile.controller;

import com.effectivemobile.model.request.LoginRequest;
import com.effectivemobile.model.request.RegisterRequest;
import com.effectivemobile.model.response.LoginResponse;
import com.effectivemobile.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Контроллер управления пользователями")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/register")
    @Operation(description = "Регистрация пользователя")
    public void register(@RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }

    @PostMapping("/login")
    @Operation(description = "Вход")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/logout")
    @Operation(description = "Выход")
    public void logout() {
        userService.logout();
    }

    @PutMapping("/friends/{friendId}")
    @Operation(description = "Добавить пользователя в друзья")
    public HttpStatus addFriend(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long friendId) {
        return userService.addFriend(userDetails.getUsername(), friendId);
    }

    @DeleteMapping("/friends/{friendId}")
    @Operation(description = "Удалить пользователя из друзей")
    public HttpStatus removeFriend(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long friendId) {
        return userService.removeFriend(userDetails.getUsername(), friendId);
    }
}