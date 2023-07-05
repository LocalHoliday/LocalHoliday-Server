package localholiday.spring.mainScreen.detailView.Review;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDTO {
    private String userId;
    private String nickname;
    private String userPhoto;
    private String reviewId;
    private String reviewPhoto;
    private String reviewContent;
    private Timestamp reviewCreated;
}
