package localholiday.spring.domain.mainScreen.detailView.Review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import localholiday.spring.domain.bill.billReview.BillReviewDTO;
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

    @PostMapping(value = "/review")
    @SecurityRequirement(name = "accessToken")
    @Operation(summary = "리뷰 작성 API 입니다", description = "Authorization 헤더에 jwt토큰이 필요, Body form 데이터로 이미지 파일과 content, target UUID를 제공해줘야함")
    public ResponseEntity<BaseResponse<?>> writeReview(@RequestHeader("Authorization") String token, @RequestBody WriteReviewDTO writeReviewDTO) {
        String uid = jwtService.parseJwtToken(token, "uid");
        String billId = reviewService.isExist(uid, writeReviewDTO.getTargetId());
        if(!redisService.isExists(uid+":app:access"))
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        if(redisService.getValues(uid+":app:access").compareTo(token.substring("Bearer ".length())) != 0)
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        else if(billId==null)
            return ResponseEntity.badRequest().body(new BaseResponse<>("리뷰를 쓸 권한이 없습니다!", BaseResponseStatus.BAD_REQUEST));
        else {
            return ResponseEntity.ok().body(new BaseResponse<>(reviewService.reviewWrite(uid, billId, writeReviewDTO.getTargetId(), "",writeReviewDTO.getContent(), writeReviewDTO.getImgURL(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()))));
        }
    }

    @PostMapping(value = "/bill/review")
    @SecurityRequirement(name="accessToken")
    @Operation(summary = "영수증 리뷰 작성 API 입니다", description = "Authorization 헤더에 jwt토큰이 필요, form 데이터로 이미지 파일과 content, billId, 제목을 제공해줘야함")
    public ResponseEntity<BaseResponse<?>> writeBillReview(@RequestHeader("Authorization") String token, @RequestBody BillReviewDTO billReviewDTO) {
        String uid = jwtService.parseJwtToken(token, "uid");
        if(!redisService.isExists(uid+":app:access"))
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        if(redisService.getValues(uid+":app:access").compareTo(token.substring("Bearer ".length())) != 0)
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        else{
            return ResponseEntity.ok().body(new BaseResponse<>(billReviewService.writeBillReview(uid, billReviewDTO.getBillId(), billReviewDTO.getContent(), billReviewDTO.getTitle(), billReviewDTO.getImgURL())));
        }
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name="accessToken")
    @Operation(summary = "이미지 업로드 api", description = "이미지 파일 보내면 이미지 url이 나옵니다.")
    public ResponseEntity<BaseResponse<String>> uploadImg(@RequestHeader("Authorization") String token, @RequestParam(value = "file") MultipartFile file) throws IOException {
        String uid = jwtService.parseJwtToken(token, "uid");
        if(!redisService.isExists(uid+":app:access"))
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        if(redisService.getValues(uid+":app:access").compareTo(token.substring("Bearer ".length())) != 0)
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        else{
            String imgURL = s3Uploader.upload(file, userService.getUserDTO(uid).getEmail());
            return ResponseEntity.ok().body(new BaseResponse<>(imgURL));
        }
    }
}
