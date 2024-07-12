package site.doto.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import site.doto.domain.chat.dto.ChatDto;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChatDto chat) {
        redisTemplate.convertAndSend("chatroom", chat);
    }
}
