package localholiday.spring.domain.mainScreen.recommend;

import localholiday.spring.domain.job.JobDTO;
import localholiday.spring.domain.tour.TourDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecommendDTO {
    private String uuid;
    private String title;
    private String location;
    private String photo;
    private JobDTO jobDTO;
    private List<TourDTO> tourList;
}
