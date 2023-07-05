package localholiday.spring.house;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class HouseDTO {
    private String uuid;
    private String name;
    private String addr;
    private String lat;
    private String lon;
    private String photo;
}
