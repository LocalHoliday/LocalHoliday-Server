package localholiday.spring.domain.tour;


import localholiday.spring.domain.ReverseGeocoding;
import localholiday.spring.domain.entity.tour.TourReview;
import localholiday.spring.domain.entity.tour.TourSpot;
import localholiday.spring.domain.tour.tourReview.TourReviewRepository;
import localholiday.spring.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourService {

    private final UserService userService;
    private final TourRepository tourRepository;
    private final TourReviewRepository tourReviewRepository;
    private final ReverseGeocoding reverseGeocoding;

    public List<TourDTO> tourList(String location){
        List<TourSpot> tourList = tourRepository.findALlByLocationCode(location);
        List<TourDTO> tourDTOList = new ArrayList<>();
        for (TourSpot tour:tourList) {
            TourDTO tmp = TourDTO.builder()
                    .uuid(tour.getId())
                    .photo(tour.getPhoto())
                    .name(tour.getName())
                    .lon(tour.getLon())
                    .lat(tour.getLat())
                    .addr(tour.getAddr())
                    .build();
            tourDTOList.add(tmp);
        }
        return tourDTOList;
    }

    public Optional<TourSpot> hasUUID(String uuid){
        return tourRepository.findById(uuid);
    }

    public List<TourReview> getReviews(String uuid){
        return tourReviewRepository.findAllByTourId(uuid);
    }
}
