package localholiday.spring.mainScreen.localPlay;

import localholiday.spring.baseResponse.BaseResponse;
import localholiday.spring.food.FoodService;
import localholiday.spring.house.HouseService;
import localholiday.spring.tour.TourService;
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

    @GetMapping("localplay")
    public ResponseEntity<BaseResponse<LocalPlayDTO>> getLocalPlay(@RequestParam String loc){
        LocalPlayDTO testDTO = new LocalPlayDTO();
        testDTO.setFoodDTOList(foodService.foodList(loc));
        testDTO.setHouseDTOList(houseService.houseList(loc));
        testDTO.setTourDTOList(tourService.tourList(loc));
        return ResponseEntity.ok().body(new BaseResponse<>(testDTO));
    }
}
