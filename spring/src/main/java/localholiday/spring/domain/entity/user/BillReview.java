package localholiday.spring.domain.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillReview {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;
    @Column(length = 36, nullable = false)
    private String userId;
    @Column(length = 36, nullable = false)
    private String billId;
    @CreatedDate
    private Timestamp created;
    @Column(length = 500)
    private String content;
    @Column(length = 100)
    private String title;
    @Column(length = 100)
    private String profile;
}
