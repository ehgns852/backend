package com.backend.moamoa.domain.chat.config;

import com.backend.moamoa.domain.chat.entity.Notice;
import com.backend.moamoa.domain.chat.entity.NoticeType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messageSendingOperations;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            Notice notice = objectMapper.readValue(publishMessage, Notice.class);
            log.debug("RedisSubscriber : " + notice.toString());
            if (notice.getType() == NoticeType.MESSAGE) {
                Long roomSubscribeId = notice.getChatRoom().getId();
                messageSendingOperations.convertAndSend("/sub/chat/rooms/" + roomSubscribeId, notice);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
