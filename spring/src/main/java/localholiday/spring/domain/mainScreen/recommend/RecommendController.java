package localholiday.spring.domain.mainScreen.recommend;

import io.swagger.v3.oas.annotations.Operation;
import localholiday.spring.global.baseResponse.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @Operation(summary = "메인화면 로컬 홀리데이 추천 리스트", description = "메인화면에서 로컬 홀리데이 추천 목록 및 상세 내용을 한번에 전달")
    @GetMapping("/recommend")
    public ResponseEntity<BaseResponse<List<RecommendDTO>>> getRecommendList(){
        return ResponseEntity.ok().body(new BaseResponse<>(recommendService.getAllRecommend()));
    }
}
