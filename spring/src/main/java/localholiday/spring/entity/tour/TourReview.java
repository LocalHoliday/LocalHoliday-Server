package localholiday.spring.entity.tour;

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
public class TourReview {

    @Id @Column(length = 36)
    private String id;
    @Column(length = 36, nullable = false)
    private String userId;
    @Column(length = 36, nullable = false)
    private String billId;
    @Column(length = 36, nullable = false)
    private String tourId;
    @CreatedDate
    private Timestamp created;
    @Column(length = 300)
    private String title;
    @Column(length = 500)
    private String content;
    @Column(length = 100)
    private String photo;
}
