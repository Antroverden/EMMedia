package com.effectivemobile.service;

import com.effectivemobile.entity.User;
import com.effectivemobile.entity.enums.RoleEnum;
import com.effectivemobile.exception.NotFoundException;
import com.effectivemobile.model.request.LoginRequest;
import com.effectivemobile.model.response.LoginResponse;
import com.effectivemobile.repository.FriendshipRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.effectivemobile.entity.Friendship;
import com.effectivemobile.model.request.RegisterRequest;
import com.effectivemobile.repository.UserRepository;
import com.effectivemobile.security.JwtProvider;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;
    FriendshipRepository friendshipRepository;

    public void register(RegisterRequest registerRequest) {
        if (userRepository.findByLogin(registerRequest.getLogin()).isPresent()) {
            throw new NotFoundException("Пользователь с таким никнеймом уже есть");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        User user = new User().setLogin(registerRequest.getLogin())
                .setPassword(passwordEncoder.encode(registerRequest.getPassword()))
                .setRole(RoleEnum.ROLE_USER);
        userRepository.save(user);
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("Нет такого пользователя"));
        String token;
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            token = jwtProvider.generateToken(loginRequest.getLogin());
        } else throw new UsernameNotFoundException("Неверный пароль");
        return new LoginResponse().setUserId(user.getId()).setUsername(user.getLogin()).setToken(token).setRole(user.getRole());
    }


    public HttpStatus addFriend(String username, Long friendId) {
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Friendship friendship = new Friendship(null, user, friend);
        friendshipRepository.save(friendship);
        return HttpStatus.OK;
    }

    public HttpStatus removeFriend(String username, Long friendId) {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        friendshipRepository.deleteByUser_IdAndUserFriend_Id(user.getId(), friendId);
        return HttpStatus.OK;
    }
}
