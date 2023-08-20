package com.effectivemobile.service;

import com.effectivemobile.entity.User;
import com.effectivemobile.exception.ConflictException;
import com.effectivemobile.exception.NotFoundException;
import com.effectivemobile.mapper.MessageMapper;
import com.effectivemobile.model.request.MessageDto;
import com.effectivemobile.repository.FriendshipRepository;
import com.effectivemobile.repository.MessageRepository;
import com.effectivemobile.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import com.effectivemobile.entity.Message;
import com.effectivemobile.model.response.MessageResponseDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {

    MessageRepository messageRepository;
    UserRepository userRepository;
    FriendshipRepository friendshipRepository;
    MessageMapper messageMapper;

    @Transactional
    public MessageResponseDto sendMessage(Long userId, MessageDto messageDto, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (friendshipRepository.existsFriendshipByUser_IdAndUserFriend_Id(user.getId(), friend.getId()) &&
                friendshipRepository.existsFriendshipByUser_IdAndUserFriend_Id(friend.getId(), user.getId())) {
            Message newMessage = Message.builder()
                    .firstUser(user)
                    .secondUser(friend)
                    .text(messageDto.getText())
                    .sentAt(LocalDateTime.now())
                    .build();
            messageRepository.save(newMessage);
            return messageMapper.toResponseDto(newMessage);
        } else {
            throw new ConflictException("Пользователи не друзья");
        }
    }

    @Transactional
    public List<Message> getHistory(Long userId, String username) {
        User friend = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (friendshipRepository.existsFriendshipByUser_IdAndUserFriend_Id(user.getId(), friend.getId()) &&
                friendshipRepository.existsFriendshipByUser_IdAndUserFriend_Id(friend.getId(), user.getId())) {
            List<Message> messagesFriendToUser = messageRepository.findAllByFirstUser_IdAndSecondUser_Id(userId, user.getId());
            List<Message> messagesUserToFriend = messageRepository.findAllByFirstUser_IdAndSecondUser_Id(user.getId(), userId);
            messagesFriendToUser.addAll(messagesUserToFriend);
//            return ChatMapper.toResponseDtos(messagesFriendToUser);
            return List.of();
        } else {
            throw new ConflictException("Пользователи не друзья");
        }
    }
}
