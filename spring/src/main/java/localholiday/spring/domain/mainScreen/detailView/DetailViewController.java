package localholiday.spring.domain.mainScreen.detailView;

import io.swagger.v3.oas.annotations.Operation;
import localholiday.spring.domain.mainScreen.detailView.Review.ReviewDTO;
import localholiday.spring.global.baseResponse.BaseResponse;
import localholiday.spring.global.baseResponse.BaseResponseStatus;
import localholiday.spring.domain.mainScreen.detailView.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DetailViewController {

    private final DetailViewService detailViewService;
    private final ReviewService reviewService;

    @Operation(summary = "놀거리 리스트 클릭시 상세정보", description = "선택한 맛집,관광지,숙소에 대한 상세정보 제공")
    @GetMapping("/play/detailview")
    public ResponseEntity<BaseResponse<?>> detailView(@RequestParam String uuid){
        DetailViewDTO detailViewDTO = detailViewService.getDetailView(uuid);
        if(detailViewDTO == null)
            return ResponseEntity.badRequest().body(new BaseResponse<>("잘못된 UUID 입니다.", BaseResponseStatus.BAD_REQUEST));
        return ResponseEntity.ok().body(new BaseResponse<>(detailViewDTO));
    }

    @Operation(summary = "놀거리 리스트 클릭시 리뷰정보", description = "선택한 맛집,관광지,숙소에 대한 리뷰 리스트 제공")
    @GetMapping("/play/reviews")
    public ResponseEntity<BaseResponse<?>> playReviews(@RequestParam String uuid){
        List<ReviewDTO> reviewDTOS = reviewService.getReviewList(uuid);
        if(reviewDTOS == null)
            return ResponseEntity.ok().body(new BaseResponse<>("UUID에 해당하는 리뷰가 없습니다."));
        return ResponseEntity.ok().body(new BaseResponse<>(reviewDTOS));
    }
}
