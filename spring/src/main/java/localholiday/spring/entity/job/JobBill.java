package localholiday.spring.entity.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobBill {

    @Id @Column(length = 36)
    private String id;
    @Column(length = 36, nullable = false)
    private String billId;
    @Column(length = 36, nullable = false)
    private String jobId;
    @CreatedDate
    private Timestamp created;

}
