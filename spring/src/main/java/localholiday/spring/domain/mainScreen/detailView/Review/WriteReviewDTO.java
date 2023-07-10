package localholiday.spring.domain.mainScreen.detailView.Review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WriteReviewDTO {
    String imgURL;
    String targetId;
    String content;
}
