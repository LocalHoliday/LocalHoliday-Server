package localholiday.spring.domain.user;

import localholiday.spring.domain.entity.user.User;
import localholiday.spring.global.jwt.JwtService;
import localholiday.spring.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final RedisService redisService;
    private final UserRepository userRepository;

    public boolean isLogin(String token){
        String uuid = jwtService.getCustomValue(token, "uuid");
        return redisService.isExists(uuid);
    }

    public UserDTO getUserDTO(String uuid){
        Optional<User> user = userRepository.findById(uuid);
        if(user.isPresent()){
            UserDTO userDTO = new UserDTO();
            userDTO.setCreated(user.get().getCreated());
            userDTO.setEmail(user.get().getEmail());
            userDTO.setNickname(user.get().getNickname());
            userDTO.setPhoto(user.get().getPhoto());
            userDTO.setUuid(user.get().getId());
            userDTO.setPhone(user.get().getPhone());
            return userDTO;
        }
        return null;
    }
}
