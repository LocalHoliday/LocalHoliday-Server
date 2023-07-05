package localholiday.spring.house;

import com.fasterxml.jackson.databind.JsonNode;
import localholiday.spring.ReverseGeocoding;
import localholiday.spring.entity.house.House;
import localholiday.spring.entity.house.HouseReview;
import localholiday.spring.house.houseReview.HouseReviewRepository;
import localholiday.spring.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final UserService userService;
    private final HouseRepository houseRepository;
    private final HouseReviewRepository houseReviewRepository;
    private final ReverseGeocoding reverseGeocoding;

    public List<HouseDTO> houseList(String location){
        List<House> houseList = houseRepository.findALlByLocationCode(location);
        List<HouseDTO> houseDTOList = new ArrayList<>();
        for (House house:houseList) {
            HouseDTO tmp = new HouseDTO();
            tmp.setAddr(house.getAddr());
            tmp.setLat(house.getLat());
            tmp.setLon(house.getLon());
            tmp.setName(house.getName());
            tmp.setPhoto(house.getPhoto());
            tmp.setUuid(house.getId());
            houseDTOList.add(tmp);
        }
        return houseDTOList;
    }

    public Optional<House> hasUUID(String uuid){
        return houseRepository.findById(uuid);
    }

    public List<HouseReview> getReviews(String uuid){
        return houseReviewRepository.findAllByHouseId(uuid);
    }
}
