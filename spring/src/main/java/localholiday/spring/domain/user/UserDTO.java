package localholiday.spring.domain.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {
    private String uuid;
    private String email;
    private String phone;
    private Timestamp created;
    private String nickname;
    private String photo;
}
