package localholiday.spring.domain.house;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "숙소 정보")
public class HouseDTO {
    @Schema(description = "house UUID")
    private String uuid;
    @Schema(description = "숙소 이름")
    private String name;
    @Schema(description = "숙소 위치")
    private String addr;
    @Schema(description = "숙소 위도")
    private String lat;
    @Schema(description = "숙소 경도")
    private String lon;
    @Schema(description = "숙소 사진 URL")
    private String photo;
}
