package localholiday.spring.entity.job;

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
public class JobStatusCode {

    @Id @Column(length = 36)
    private String id;
    @Column(length = 100)
    private String code;

}
