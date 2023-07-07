package localholiday.spring.domain.mainScreen.reservation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReservationDTO {
    String start;
    String end;
    String location;
    List<String> uuid;
}
