package localholiday.spring.domain.house.houseReview;

import localholiday.spring.domain.entity.house.HouseReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseReviewRepository extends JpaRepository<HouseReview, String> {

    List<HouseReview> findAllByHouseId(String houseId);
    HouseReview findByUserIdAndBillIdAndHouseId(String userId, String billId, String houseId);
}
