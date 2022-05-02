package com.backend.moamoa.domain.chat.controller;

import com.backend.moamoa.domain.chat.config.RedisSubscriber;
import com.backend.moamoa.domain.chat.dto.CreateChatRoomRequest;
import com.backend.moamoa.domain.chat.entity.ChatRoom;
import com.backend.moamoa.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;

    @PostMapping("/new/room")
    public ResponseEntity<ChatRoom> createRoom(@RequestBody CreateChatRoomRequest request) {
        ChatRoom createdRoom = chatRoomService.createRoom(request);
        // redis 방입장 구독
        ChannelTopic topic = new ChannelTopic("/rooms/" + createdRoom.getId());
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }
}
