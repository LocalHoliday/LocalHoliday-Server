package localholiday.spring.domain.mainScreen.localPlay;

import io.swagger.v3.oas.annotations.Operation;
import localholiday.spring.global.baseResponse.BaseResponse;
import localholiday.spring.domain.food.FoodService;
import localholiday.spring.domain.house.HouseService;
import localholiday.spring.domain.tour.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LocalPlayController {

    private final FoodService foodService;
    private final HouseService houseService;
    private final TourService tourService;

    @Operation(summary = "지역 놀거리 목록", description = "선택한 지역의 맛집,관광지,숙소를 한번에 제공")
    @GetMapping("play")
    public ResponseEntity<BaseResponse<LocalPlayDTO>> getLocalPlay(@RequestParam String loc){
        LocalPlayDTO playDTO = new LocalPlayDTO();
        playDTO.setFoodDTOList(foodService.foodList(loc));
        playDTO.setHouseDTOList(houseService.houseList(loc));
        playDTO.setTourDTOList(tourService.tourList(loc));
        return ResponseEntity.ok().body(new BaseResponse<>(playDTO));
    }
}
