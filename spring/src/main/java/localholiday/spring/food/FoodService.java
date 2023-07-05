package localholiday.spring.food;

import com.fasterxml.jackson.databind.JsonNode;
import localholiday.spring.ReverseGeocoding;
import localholiday.spring.entity.food.Food;
import localholiday.spring.entity.food.FoodReview;
import localholiday.spring.food.foodReview.FoodReviewRepository;
import localholiday.spring.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final UserService userService;
    private final FoodRepository foodRepository;
    private final FoodReviewRepository foodReviewRepository;
    private final ReverseGeocoding reverseGeocoding;

    public List<FoodDTO> foodList(String location){
        List<Food> foodList = foodRepository.findALlByLocationCode(location);
        List<FoodDTO> foodDTOList = new ArrayList<>();
        for (Food food:foodList) {
            FoodDTO tmp = new FoodDTO();
            tmp.setAddr(food.getAddr());
            tmp.setLat(food.getLat());
            tmp.setLon(food.getLon());
            tmp.setName(food.getName());
            tmp.setPhoto(food.getPhoto());
            tmp.setUuid(food.getId());
            foodDTOList.add(tmp);
        }
        return foodDTOList;
    }

    public Optional<Food> hasUUID(String uuid){
        return foodRepository.findById(uuid);
    }

    public List<FoodReview> getReviews(String uuid){
        return foodReviewRepository.findAllByFoodId(uuid);
    }
}
