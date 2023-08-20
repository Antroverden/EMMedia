package com.effectivemobile.controller;

import com.effectivemobile.entity.Message;
import com.effectivemobile.model.request.MessageDto;
import com.effectivemobile.model.response.MessageResponseDto;
import com.effectivemobile.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
@Tag(name = "Контроллер управления сообщениями")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {

    MessageService messageService;

    @Operation(summary = "Отправить сообщение пользователю")
    @PostMapping("{userId}")
    public MessageResponseDto sendMessage(@PathVariable Long userId,
                                          @RequestBody @Valid MessageDto messageDto,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        return messageService.sendMessage(userId, messageDto, userDetails.getUsername());
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Получить историю сообщений с юзером")
    public List<Message> getMessages(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return messageService.getHistory(id, userDetails.getUsername());
    }
}
