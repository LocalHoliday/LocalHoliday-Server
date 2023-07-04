package localholiday.spring.tour;


import com.fasterxml.jackson.databind.JsonNode;
import localholiday.spring.ReverseGeocoding;
import localholiday.spring.entity.food.Food;
import localholiday.spring.entity.tour.TourSpot;
import localholiday.spring.food.FoodDTO;
import localholiday.spring.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourService {

    private final UserService userService;
    private final TourRepository tourRepository;
    private final ReverseGeocoding reverseGeocoding;

    public List<TourDTO> tourList(String location){
        List<TourSpot> tourList = tourRepository.findALlByLocationCode(location);
        List<TourDTO> tourDTOList = new ArrayList<>();
        for (TourSpot tour:tourList) {
            TourDTO tmp = new TourDTO();
            tmp.setLat(tour.getLat());
            tmp.setLon(tour.getLon());
            tmp.setName(tour.getName());
            tmp.setPhoto(tour.getPhoto());
            tmp.setUuid(tour.getId());
            tourDTOList.add(tmp);
        }
        return tourDTOList;
    }
}
