package localholiday.spring.domain.mainScreen.detailView.Review;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ReviewDTO {
    private String userId;
    private String nickname;
    private String userPhoto;
    private String reviewId;
    private String reviewPhoto;
    private String reviewContent;
    private Timestamp reviewCreated;


}
