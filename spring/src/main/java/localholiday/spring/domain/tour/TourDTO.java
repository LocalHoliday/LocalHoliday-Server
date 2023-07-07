package localholiday.spring.domain.tour;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TourDTO {
    private String uuid;
    private String name;
    private String addr;
    private String lat;
    private String lon;
    private String photo;
}
