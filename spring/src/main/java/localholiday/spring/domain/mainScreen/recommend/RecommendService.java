package localholiday.spring.domain.mainScreen.recommend;

import localholiday.spring.domain.entity.job.Job;
import localholiday.spring.domain.entity.tour.TourSpot;
import localholiday.spring.domain.entity.user.Recommend;
import localholiday.spring.domain.job.JobDTO;
import localholiday.spring.domain.job.JobRepository;
import localholiday.spring.domain.tour.TourDTO;
import localholiday.spring.domain.tour.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final JobRepository jobRepository;
    private final TourRepository tourRepository;

    private TourDTO getTourDTO(String uuid){
        Optional<TourSpot> tourSpot = tourRepository.findById(uuid);
        return tourSpot.map(spot -> TourDTO.builder()
                .addr(spot.getAddr())
                .lat(spot.getLat())
                .lon(spot.getLon())
                .name(spot.getName())
                .photo(spot.getPhoto())
                .uuid(spot.getId())
                .build()).orElse(null);
    }

    private JobDTO getJobDTO(String uuid){
        Optional<Job> job = jobRepository.findById(uuid);
        return job.map(value -> JobDTO.builder()
                .addr(value.getLocation())
                .endTime(value.getEndDate())
                .startTime(value.getStartDate())
                .name(value.getName())
                .pay(value.getPayment())
                .photo(value.getPhoto())
                .uuid(value.getId())
                .build()).orElse(null);
    }

    public List<RecommendDTO> getAllRecommend(){
        List<Recommend> recommendList = recommendRepository.findAllByIdNotNull();

        List<RecommendDTO> recommendDTOList = new ArrayList<>();
        for(Recommend recommend : recommendList){
            List<TourDTO> tourSpots = new ArrayList<>();
            TourDTO t1 = getTourDTO(recommend.getTourId());
            TourDTO t2 = getTourDTO(recommend.getFoodId());
            TourDTO t3 = getTourDTO(recommend.getHouseId());
            tourSpots.add(t1);
            tourSpots.add(t2);
            tourSpots.add(t3);

            RecommendDTO recommendDTO = RecommendDTO.builder()
                    .jobDTO(getJobDTO(recommend.getJobId()))
                    .location(t1.getAddr())
                    .title(recommend.getTitle())
                    .tourList(tourSpots)
                    .photo(t1.getPhoto())
                    .uuid(recommend.getId())
                    .build();

            recommendDTOList.add(recommendDTO);
        }
        return recommendDTOList;
    }


}
