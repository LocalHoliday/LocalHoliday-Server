package localholiday.spring.domain.food;

import localholiday.spring.global.baseResponse.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("localfood")
    public ResponseEntity<BaseResponse<List<FoodDTO>>> getFood(@RequestParam String loc){
        return ResponseEntity.ok().body(new BaseResponse<>(foodService.foodList(loc)));
    }
}
