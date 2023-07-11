package localholiday.spring.domain.mainScreen;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import localholiday.spring.domain.user.UserService;
import localholiday.spring.global.baseResponse.BaseResponse;
import localholiday.spring.global.baseResponse.BaseResponseStatus;
import localholiday.spring.global.jwt.JwtService;
import localholiday.spring.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final RedisService redisService;

    @DeleteMapping("/user/delete")
    @SecurityRequirement(name="accessToken")
    @Operation(summary = "회원 탈퇴하기", description = "현재 로그인 중인 회원의 정보를 삭제합니다.")
    public ResponseEntity<BaseResponse<String>> deleteUser(@RequestHeader(value="Authorization") String token){
        String uid = jwtService.parseJwtToken(token, "uid");
        if(!redisService.isExists(uid+":app:access"))
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        if(redisService.getValues(uid+":app:access").compareTo(token.substring("Bearer ".length())) != 0)
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        else {
            redisService.delValues(uid+":app:access");
            userService.deleteUser(uid);
            return ResponseEntity.ok().body(new BaseResponse<>(uid+"가 정상적으로 탈퇴됐습니다."));
        }
    }

    @PostMapping("/logout")
    @SecurityRequirement(name="accessToken")
    @Operation(summary = "회원 탈퇴하기", description = "현재 로그인 중인 회원의 정보를 삭제합니다.")
    public ResponseEntity<BaseResponse<String>> logoutUser(@RequestHeader(value="Authorization") String token){
        String uid = jwtService.parseJwtToken(token, "uid");
        if(!redisService.isExists(uid+":app:access"))
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        if(redisService.getValues(uid+":app:access").compareTo(token.substring("Bearer ".length())) != 0)
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        else {
            redisService.delValues(uid+":app:access");
            return ResponseEntity.ok().body(new BaseResponse<>(uid+"가 정상적으로 로그아웃했습니다."));
        }
    }
}
