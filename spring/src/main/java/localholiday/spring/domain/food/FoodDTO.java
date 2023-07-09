package localholiday.spring.domain.food;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "음식점 정보")
public class FoodDTO {
    @Schema(description = "음식점UUID")
    private String uuid;
    @Schema(description = "음식점 이름")
    private String name;
    @Schema(description = "음식점 주소")
    private String addr;
    private String lat;
    private String lon;
    @Schema(description = "음식점 사진 URL")
    private String photo;
}
