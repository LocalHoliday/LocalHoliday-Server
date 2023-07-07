package localholiday.spring.global.redis;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    public String getValues(String key){
        // opsForValue : String을 쉽게 직렬화 / 역직렬화 시켜주는 인터페이스
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void setValues(String key, String value){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    public void delValues(String key){
        redisTemplate.delete(key);
    }

    public boolean isExists(String key){
        return redisTemplate.hasKey(key);
    }
}
