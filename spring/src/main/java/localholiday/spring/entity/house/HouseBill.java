package localholiday.spring.entity.house;

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
public class HouseBill {

    @Id @Column(length = 36)
    private String id;
    @Column(length = 36, nullable = false)
    private String billId;
    @Column(length = 36, nullable = false)
    private String houseId;
    @CreatedDate
    private Timestamp created;
}
