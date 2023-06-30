package localholiday.spring.entity.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Food {

    @Id @Column(length = 36)
    private String id;
    @Column(length = 100)
    private String name;
    @Column(length = 30)
    private String lat;
    @Column(length = 30)
    private String lon;
    @Column(length = 300)
    private String info;
    @Column(length = 100)
    private String field;
    @Column(length = 100)
    private String photo;

}
