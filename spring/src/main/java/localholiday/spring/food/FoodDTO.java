package localholiday.spring.food;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {
    private String uuid;
    private String name;
    private String lat;
    private String lon;
    private String photo;
}
