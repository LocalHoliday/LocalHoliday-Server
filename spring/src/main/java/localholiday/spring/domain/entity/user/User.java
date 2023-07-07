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
public class User {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;
    @Column(length = 100)
    private String name;
    @Column(length = 11)
    private String phone;
    @Column(length = 300)
    private String email;
    @Column(length = 100)
    private String password;
    @CreatedDate
    private Timestamp created;
    @Column(length = 100)
    private String photo;
    @Column(length = 100)
    private String nickname;
    @Column(length = 300)
    private String place;

}
