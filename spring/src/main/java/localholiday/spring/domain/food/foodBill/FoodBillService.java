package localholiday.spring.domain.food.foodBill;

import localholiday.spring.domain.entity.food.FoodBill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class FoodBillService {
    private final FoodBillRepository foodBillRepository;

    @Transactional
    public String makeBill(String billId, String foodId){
        return foodBillRepository.save(FoodBill.builder()
                        .billId(billId)
                        .foodId(foodId)
                        .created(new Timestamp(System.currentTimeMillis()).toString())
                .build()).getId();
    }
}
