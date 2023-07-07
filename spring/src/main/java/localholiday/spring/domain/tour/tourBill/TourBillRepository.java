package localholiday.spring.domain.tour.tourBill;

import localholiday.spring.domain.entity.tour.TourSpotBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourBillRepository extends JpaRepository<TourSpotBill, String> {
    Optional<TourSpotBill> findByBillIdAndTourSpotId(String billId, String tourSpotId);
}
