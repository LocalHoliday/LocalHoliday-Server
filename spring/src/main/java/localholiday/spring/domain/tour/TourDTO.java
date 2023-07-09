package localholiday.spring.domain.tour;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "관광지 정보")
public class TourDTO {
    @Schema(description = "관광지 UUID")
    private String uuid;
    @Schema(description = "관광지 이름")
    private String name;
    @Schema(description = "관광지 주소")
    private String addr;
    private String lat;
    private String lon;
    @Schema(description = "관광지 사진 URL")
    private String photo;
}
