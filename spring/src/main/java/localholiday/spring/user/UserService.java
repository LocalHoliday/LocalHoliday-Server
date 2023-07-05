package localholiday.spring.user;

import localholiday.spring.jwt.JwtService;
import localholiday.spring.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final RedisService redisService;

    public boolean isLogin(String token){
        String uuid = jwtService.getCustomValue(token, "uuid");
        return redisService.isExists(uuid);
    }
}
