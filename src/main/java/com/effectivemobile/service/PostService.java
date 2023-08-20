package com.effectivemobile.service;

import com.effectivemobile.entity.User;
import com.effectivemobile.exception.ForbiddenException;
import com.effectivemobile.exception.NotFoundException;
import com.effectivemobile.model.response.PostResponseDto;
import com.effectivemobile.repository.FriendshipRepository;
import com.effectivemobile.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.effectivemobile.entity.Friendship;
import com.effectivemobile.entity.Image;
import com.effectivemobile.entity.Post;
import com.effectivemobile.mapper.PostMapper;
import com.effectivemobile.model.request.PostRequestDto;
import com.effectivemobile.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    PostRepository postRepository;
    UserRepository userRepository;
    FriendshipRepository friendshipRepository;
    ImageService imageService;
    PostMapper postMapper;

    @Transactional
    public PostResponseDto postNewPost(String username, PostRequestDto postDto) {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
        List<Image> images = imageService.uploadImages(postDto.getImages());
        Post post = Post.builder()
                .header(postDto.getHeader())
                .text(postDto.getText())
                .images(images)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.save(post);
        return postMapper.toResponseDto(post);
    }

    @Transactional
    public List<PostResponseDto> getUserPosts(Long userId, int size, int page) {
        if (userRepository.existsById(userId)) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            List<Post> posts = postRepository.findAllByUser_Id(userId, pageable);
            return postMapper.toResponseDtos(posts);
        }
        return Collections.emptyList();
    }

    @Transactional
    public PostResponseDto updatePost(String username, PostRequestDto postDto, Long postId) {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Такого поста не существует"));
        if (!user.equals(post.getUser()))
            throw new ForbiddenException("Пользователь не может изменить этот пост");
        post.setHeader(postDto.getHeader());
        post.setText(postDto.getText());
        if (postDto.getImages() != null) {
            List<Image> images = imageService.uploadImages(postDto.getImages());
            post.setImages(images);
        }
        return postMapper.toResponseDto(postRepository.save(post));
    }

    @Transactional
    public void deletePost(String username, Long postId) {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Такого поста не существует"));
        if (!user.equals(post.getUser()))
            throw new ForbiddenException("Пользователь не может удалить этот пост");
        postRepository.delete(post);
    }

    @Transactional
    public List<PostResponseDto> getUserFeed(String username, int size, int page, String order) {
        List<Friendship> userFriendships = friendshipRepository.findAllByUser_Login(username);
        if (userFriendships.isEmpty()) {
            throw new NotFoundException("Пользователь ни на кого не подписался");
        }
        List<Long> userIdList = userFriendships.stream()
                .map(friendship -> friendship.getUserFriend().getId())
                .toList();
        Pageable pageable = null;
        switch (order) {
            case "desc" -> pageable = PageRequest.of(page, size, Sort.by("creation").descending());
            case "asc" -> pageable = PageRequest.of(page, size, Sort.by("creation").ascending());
        }
        List<Post> posts = postRepository.findAllByUser_IdIn(userIdList, pageable);
        return postMapper.toResponseDtos(posts);
    }
}
