package com.effectivemobile.controller;

import com.effectivemobile.model.request.PostRequestDto;
import com.effectivemobile.model.response.PostResponseDto;
import com.effectivemobile.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@Tag(name = "Контроллер управления постами")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;

    @Operation(summary = "Создать новый пост")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping(produces = {"application/json", "image/jpg"})
    public PostResponseDto postNewPost(@Valid @RequestPart() PostRequestDto post,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return postService.postNewPost(userDetails.getUsername(), post);
    }

    @Operation(summary = "Получить посты юзера")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/{userId}")
    public List<PostResponseDto> getUserPosts(@PathVariable() Long userId,
                                              @RequestParam(required = false, defaultValue = "10") Integer size,
                                              @RequestParam(required = false, defaultValue = "0") Integer page) {
        return postService.getUserPosts(userId, size, page);
    }

    @Operation(summary = "Обновить свой пост")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PutMapping
    public PostResponseDto updatePost(@Valid @RequestPart(required = false) PostRequestDto post,
                                      @RequestParam(value = "post-id") Long postId,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        return postService.updatePost(userDetails.getUsername(), post, postId);
    }

    @Operation(summary = "Удалить свой пост")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @DeleteMapping
    public void deletePost(@RequestParam(value = "post-id") Long postId,
                           @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(userDetails.getUsername(), postId);
    }

    @Operation(summary = "Получить последние посты от пользователей, на которых подписан")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/feed")
    public List<PostResponseDto> getUserFeed(@RequestParam(required = false, defaultValue = "10") int size,
                                             @RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "desc") String order,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return postService.getUserFeed(userDetails.getUsername(), size, page, order);
    }
}
