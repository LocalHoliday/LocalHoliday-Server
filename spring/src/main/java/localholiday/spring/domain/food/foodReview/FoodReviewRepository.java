package localholiday.spring.domain.food.foodReview;

import localholiday.spring.domain.entity.food.FoodReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodReviewRepository extends JpaRepository<FoodReview, String> {

    List<FoodReview> findAllByFoodId(String foodId);
}
