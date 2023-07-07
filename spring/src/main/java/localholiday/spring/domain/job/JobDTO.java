package localholiday.spring.domain.job;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class JobDTO {
    private String uuid;
    private String name;
    private String addr;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer pay;
    private String photo;
}
