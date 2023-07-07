package localholiday.spring.domain.tour.tourReview;

import localholiday.spring.domain.entity.tour.TourReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourReviewRepository extends JpaRepository<TourReview, String> {

    List<TourReview> findAllByTourId(String tourId);
}
