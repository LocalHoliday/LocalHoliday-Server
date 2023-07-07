package localholiday.spring.domain.mainScreen.detailView;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailViewDTO {
    private String uuid;
    private String name;
    private String addr;
    private String lat;
    private String lon;
    private String info;
    private String photo;

}
