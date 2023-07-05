package localholiday.spring.mainScreen.detailView;

import localholiday.spring.entity.food.Food;
import localholiday.spring.entity.house.House;
import localholiday.spring.entity.tour.TourSpot;
import localholiday.spring.food.FoodService;
import localholiday.spring.house.HouseService;
import localholiday.spring.tour.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetailViewService {

    private final FoodService foodService;
    private final HouseService houseService;
    private final TourService tourService;

    private DetailViewDTO getField(String uuid, String name, String addr, String lat, String lon, String photo, String info){
        DetailViewDTO detailViewDTO = new DetailViewDTO();
        detailViewDTO.setAddr(addr);
        detailViewDTO.setName(name);
        detailViewDTO.setLat(lat);
        detailViewDTO.setLon(lon);
        detailViewDTO.setPhoto(photo);
        detailViewDTO.setUuid(uuid);
        detailViewDTO.setInfo(info);

        return detailViewDTO;
    }

    public DetailViewDTO getDetailView(String uuid){
        Optional<Food> food = foodService.hasUUID(uuid);
        Optional<House> house = houseService.hasUUID(uuid);
        Optional<TourSpot> tour = tourService.hasUUID(uuid);
        if(food.isPresent()){
            return getField(uuid, food.get().getName(), food.get().getAddr(), food.get().getLat(), food.get().getLon(), food.get().getPhoto(), food.get().getInfo());
        }
        else if(house.isPresent()){
            return getField(uuid, house.get().getName(), house.get().getAddr(), house.get().getLat(), house.get().getLon(), house.get().getPhoto(), "");
        }
        else if(tour.isPresent()){
            return getField(uuid, tour.get().getName(), tour.get().getAddr(), tour.get().getLat(), tour.get().getLon(), tour.get().getPhoto(), tour.get().getInfo());
        }
        return null;
    }
}
