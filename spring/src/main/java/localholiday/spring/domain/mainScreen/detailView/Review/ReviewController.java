package localholiday.spring.domain.mainScreen.detailView.Review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import localholiday.spring.domain.bill.billReview.BillReviewService;
import localholiday.spring.domain.user.UserService;
import localholiday.spring.global.baseResponse.BaseResponse;
import localholiday.spring.global.baseResponse.BaseResponseStatus;
import localholiday.spring.global.jwt.JwtService;
import localholiday.spring.global.redis.RedisService;
import localholiday.spring.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final BillReviewService billReviewService;
    private final S3Uploader s3Uploader;
    private final JwtService jwtService;
    private final RedisService redisService;
    private final UserService userService;

    @PostMapping(value = "/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "accessToken")
    @Operation(summary = "리뷰 작성 API 입니다", description = "Authorization 헤더에 jwt토큰이 필요, Body form 데이터로 이미지 파일과 content, target UUID를 제공해줘야함")
    public ResponseEntity<BaseResponse<?>> writeReview(@RequestHeader("Authorization") String token, @Parameter(description = "MultipartFile 형식의 이미지 파일") @RequestParam(value = "file")MultipartFile file, @RequestParam String content, @Parameter(description = "관광지/맛집/숙소/알바의 UID 입력")@RequestParam String targetId) throws IOException {
        String uid = jwtService.parseJwtToken(token, "uid");
        String billId = reviewService.isExist(uid, targetId);
        if(!redisService.isExists(uid+":app:access"))
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        if(redisService.getValues(uid+":app:access").compareTo(token.substring("Bearer ".length())) != 0)
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        else if(billId==null)
            return ResponseEntity.badRequest().body(new BaseResponse<>("리뷰를 쓸 권한이 없습니다!", BaseResponseStatus.BAD_REQUEST));
        else {
            String imgURL = s3Uploader.upload(file, userService.getUserDTO(uid).getEmail());
            return ResponseEntity.ok().body(new BaseResponse<>(reviewService.reviewWrite(uid, billId, targetId, "",content, imgURL, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()))));
        }
    }

    @PostMapping(value = "/bill/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name="accessToken")
    @Operation(summary = "영수증 리뷰 작성 API 입니다", description = "Authorization 헤더에 jwt토큰이 필요, form 데이터로 이미지 파일과 content, billId, 제목을 제공해줘야함")
    public ResponseEntity<BaseResponse<?>> writeBillReview(@RequestHeader("Authorization") String token, @Parameter(description = "MultipartFile 형식의 이미지 파일") @RequestParam(value = "file")MultipartFile file, @RequestParam String content, @Parameter(description = "리뷰를 작성할 영수증의 UID") @RequestParam String billId, @RequestParam String title) throws  IOException{
        String uid = jwtService.parseJwtToken(token, "uid");
        if(!redisService.isExists(uid+":app:access"))
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        if(redisService.getValues(uid+":app:access").compareTo(token.substring("Bearer ".length())) != 0)
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        else{
            String imgURL = s3Uploader.upload(file, userService.getUserDTO(uid).getEmail());
            return ResponseEntity.ok().body(new BaseResponse<>(billReviewService.writeBillReview(uid, billId, content, title, imgURL)));
        }
    }
}
