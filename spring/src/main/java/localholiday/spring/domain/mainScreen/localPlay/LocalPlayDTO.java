package localholiday.spring.domain.mainScreen.localPlay;

import localholiday.spring.domain.food.FoodDTO;
import localholiday.spring.domain.house.HouseDTO;
import localholiday.spring.domain.tour.TourDTO;
import lombok.Data;

import java.util.List;

@Data
public class LocalPlayDTO {
    List<FoodDTO> foodDTOList;
    List<HouseDTO> houseDTOList;
    List<TourDTO> tourDTOList;
}
