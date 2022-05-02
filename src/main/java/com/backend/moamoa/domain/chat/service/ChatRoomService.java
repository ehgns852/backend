package com.backend.moamoa.domain.chat.service;

import com.backend.moamoa.domain.chat.dto.CreateChatRoomRequest;
import com.backend.moamoa.domain.chat.entity.ChatRoom;
import com.backend.moamoa.domain.chat.repository.ChatRoomRepository;
import com.backend.moamoa.domain.user.entity.User;
import com.backend.moamoa.domain.user.repository.UserRepository;
import com.backend.moamoa.global.exception.CustomException;
import com.backend.moamoa.global.exception.ErrorCode;
import com.backend.moamoa.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoom createRoom(CreateChatRoomRequest request) {
        User host = userUtil.findCurrentUser();
        User guest = userRepository.findById(request.getGuestId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

        return chatRoomRepository.save(ChatRoom.builder()
                .host(host)
                .guest(guest)
                .build());
    }
}
