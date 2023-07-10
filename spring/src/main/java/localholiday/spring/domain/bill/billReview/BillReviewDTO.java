package localholiday.spring.domain.bill.billReview;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillReviewDTO {
    String title;
    String imgURL;
    String billId;
    String content;
}
