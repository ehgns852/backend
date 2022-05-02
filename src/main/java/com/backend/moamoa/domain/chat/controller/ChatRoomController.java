package com.backend.moamoa.domain.chat.controller;

import com.backend.moamoa.domain.chat.config.RedisSubscriber;
import com.backend.moamoa.domain.chat.dto.CreateChatRoomRequest;
import com.backend.moamoa.domain.chat.dto.CreateRoomResponse;
import com.backend.moamoa.domain.chat.entity.ChatRoom;
import com.backend.moamoa.domain.chat.service.ChatRoomService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiOperation(value = "채팅 신청", notes = "Guest 유저 PK를 받아 채팅 신청을 하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "성공적으로 채팅방을 생성한 경우"),
            @ApiResponse(responseCode = "404", description = "Host OR Guest Id를 찾지 못한 경우")
    })
    @PostMapping("/new/room")
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody CreateChatRoomRequest request) {
        Long roomId = chatRoomService.createRoom(request);
        // redis 방입장 구독
        ChannelTopic topic = new ChannelTopic("/rooms/" + roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateRoomResponse(roomId));
    }
}
