package localholiday.spring.domain.house.houseBill;

import localholiday.spring.domain.entity.house.HouseBill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class HouseBillService {
    private final HouseBillRepository houseBillRepository;

    @Transactional
    public String makeBill(String billId, String houseId){
        return houseBillRepository.save(HouseBill.builder()
                        .billId(billId)
                        .houseId(houseId)
                        .created(new Timestamp(System.currentTimeMillis()))
                .build()).getId();
    }
}
