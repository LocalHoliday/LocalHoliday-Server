package localholiday.spring.domain.mainScreen.localPlay;

import io.swagger.v3.oas.annotations.media.Schema;
import localholiday.spring.domain.food.FoodDTO;
import localholiday.spring.domain.house.HouseDTO;
import localholiday.spring.domain.tour.TourDTO;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "놀거리 목록DTO")
public class LocalPlayDTO {
    @Schema(description = "foodDTO 리스트")
    List<FoodDTO> foodDTOList;
    @Schema(description = "houseDTO 리스트")
    List<HouseDTO> houseDTOList;
    @Schema(description = "tourDTO 리스트")
    List<TourDTO> tourDTOList;
}
