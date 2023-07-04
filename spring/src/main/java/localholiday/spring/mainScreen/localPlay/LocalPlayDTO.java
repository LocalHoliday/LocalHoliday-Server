package localholiday.spring.mainScreen.localPlay;

import localholiday.spring.food.FoodDTO;
import localholiday.spring.house.HouseDTO;
import localholiday.spring.tour.TourDTO;
import lombok.Data;

import java.util.List;

@Data
public class LocalPlayDTO {
    List<FoodDTO> foodDTOList;
    List<HouseDTO> houseDTOList;
    List<TourDTO> tourDTOList;
}
