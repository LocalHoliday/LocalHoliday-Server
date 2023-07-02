package localholiday.spring.entity.tour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourSpot {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;
    @Column(length = 100)
    private String name;
    @Column(length = 30)
    private String lat;
    @Column(length = 30)
    private String lon;
    @Column(length = 300)
    private String info;
    private Byte locCode;
    @Column(length = 100)
    private String photo;
}
