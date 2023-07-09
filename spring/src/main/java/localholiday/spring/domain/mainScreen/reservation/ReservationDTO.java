package localholiday.spring.domain.mainScreen.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "예약DTO")
public class ReservationDTO {
    @Schema(description = "시작일시", example = "2023-07-03 00:00:00.000")
    String start;
    @Schema(description = "끝일시", example = "2023-07-22 00:00:00.0000")
    String end;
    @Schema(description = "위치")
    String location;
    @Schema(description = "내가 담아둔것들의 UUID 리스트", example = "135145-132414-135123")
    List<String> uuid;
}
