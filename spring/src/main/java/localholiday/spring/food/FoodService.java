package localholiday.spring.food;

import com.fasterxml.jackson.databind.JsonNode;
import localholiday.spring.ReverseGeocoding;
import localholiday.spring.entity.food.Food;
import localholiday.spring.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final UserService userService;
    private final FoodRepository foodRepository;
    private final ReverseGeocoding reverseGeocoding;

    public List<FoodDTO> foodList(String location){
        List<Food> foodList = foodRepository.findALlByLocationCode(location);
        List<FoodDTO> foodDTOList = new ArrayList<>();
        for (Food food:foodList) {
            FoodDTO tmp = new FoodDTO();
            tmp.setLat(food.getLat());
            tmp.setLon(food.getLon());
            tmp.setName(food.getName());
            tmp.setPhoto(food.getPhoto());
            tmp.setUuid(food.getId());
            foodDTOList.add(tmp);
        }
        return foodDTOList;
    }
}
