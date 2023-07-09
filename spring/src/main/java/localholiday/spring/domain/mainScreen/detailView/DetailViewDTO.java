package localholiday.spring.domain.mainScreen.detailView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "상세정보")
public class DetailViewDTO {
    @Schema(description = "UUID")
    private String uuid;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "주소")
    private String addr;
    @Schema(description = "위도")
    private String lat;
    @Schema(description = "경도")
    private String lon;
    @Schema(description = "상세소개")
    private String info;
    @Schema(description = "이미지 주소")
    private String photo;

}
