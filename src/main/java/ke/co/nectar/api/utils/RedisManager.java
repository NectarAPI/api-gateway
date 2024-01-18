package ke.co.nectar.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    public void store(String key, String value, int seconds) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, Duration.ofSeconds(seconds));
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
