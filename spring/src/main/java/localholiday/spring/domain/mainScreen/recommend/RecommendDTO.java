package localholiday.spring.domain.mainScreen.recommend;

import io.swagger.v3.oas.annotations.media.Schema;
import localholiday.spring.domain.job.JobDTO;
import localholiday.spring.domain.tour.TourDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "로컬홀리데이 추천목록")
public class RecommendDTO {
    @Schema(description = "추천UUID")
    private String uuid;
    @Schema(description = "추천 제목")
    private String title;
    @Schema(description = "위치")
    private String location;
    @Schema(description = "대표사진")
    private String photo;
    @Schema(description = "알바 정보")
    private JobDTO jobDTO;
    @Schema(description = "관광지 정보들")
    private List<TourDTO> tourList;
}
