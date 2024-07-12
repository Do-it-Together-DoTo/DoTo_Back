package site.doto.global.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import site.doto.domain.chat.dto.ChatDto;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messageTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());

            ChatDto chatDto = objectMapper.readValue(publishMessage, ChatDto.class);

            messageTemplate.convertAndSend("/sub/chatting/" + chatDto.getChatRoomId(), chatDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
