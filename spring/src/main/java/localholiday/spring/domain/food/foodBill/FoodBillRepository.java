package localholiday.spring.domain.food.foodBill;

import localholiday.spring.domain.entity.food.FoodBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodBillRepository extends JpaRepository<FoodBill, String> {
    Optional<FoodBill> findByBillIdAndFoodId(String billId, String foodId);
}
