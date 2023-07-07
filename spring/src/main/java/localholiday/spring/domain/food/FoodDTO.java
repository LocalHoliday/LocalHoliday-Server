package localholiday.spring.domain.food;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {
    private String uuid;
    private String name;
    private String addr;
    private String lat;
    private String lon;
    private String photo;
}
