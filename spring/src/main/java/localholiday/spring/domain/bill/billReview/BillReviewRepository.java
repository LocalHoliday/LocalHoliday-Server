package localholiday.spring.domain.bill.billReview;

import localholiday.spring.domain.entity.user.BillReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillReviewRepository extends JpaRepository<BillReview, String> {

}
