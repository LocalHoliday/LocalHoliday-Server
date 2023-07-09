package localholiday.spring.domain.bill.billReview;

import localholiday.spring.domain.entity.user.BillReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillReviewService {

    private final BillReviewRepository billReviewRepository;

    public String writeBillReview(String userId, String billId, String content, String title, String photoURL){
        return billReviewRepository.save(BillReview.builder()
                        .billId(billId)
                        .content(content)
                        .created(new Timestamp(System.currentTimeMillis()))
                        .userId(userId)
                        .title(title)
                        .profile(photoURL)
                .build()).getId();
    }
}
