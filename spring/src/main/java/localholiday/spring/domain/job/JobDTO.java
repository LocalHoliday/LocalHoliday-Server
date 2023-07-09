package localholiday.spring.domain.job;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@Schema(description = "알바정보")
public class JobDTO {
    @Schema(description = "알바UUID")
    private String uuid;
    @Schema(description = "알바 이름")
    private String name;
    @Schema(description = "알바 주소")
    private String addr;
    @Schema(description = "시작일시", example = "2023-07-03")
    private Timestamp startTime;
    @Schema(description = "종료일시", example = "2023-07-03")
    private Timestamp endTime;
    @Schema(description = "급여, 협의는 0원")
    private Integer pay;
    @Schema(description = "알바사진 URL")
    private String photo;
}
