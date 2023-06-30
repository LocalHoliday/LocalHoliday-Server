package localholiday.spring.entity.user;

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
public class Bill {
    @Id @Column(length = 36)
    private String id;
    @Column(length = 36, nullable = false)
    private String userId;
    @CreatedDate
    private Timestamp created;
    private Byte code;
    private Timestamp startDate;
    private Timestamp endDate;
    @Column(length = 100)
    private String location;
}
