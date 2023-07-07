package localholiday.spring.domain.entity.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommend {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;
    @Column(length = 36, nullable = false)
    private String foodId;
    @Column(length = 36, nullable = false)
    private String houseId;
    @Column(length = 36, nullable = false)
    private String jobId;
    @Column(length = 36, nullable = false)
    private String tourId;
    @Column(length = 100)
    private String title;
}
