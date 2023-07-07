package localholiday.spring.domain.bill;

import localholiday.spring.domain.entity.user.Bill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    @Transactional
    public String reservation(String userId, String start, String end, String location){
        return billRepository.save(Bill.builder()
                        .userId(userId)
                        .endDate(Timestamp.valueOf(start))
                        .startDate(Timestamp.valueOf(end))
                        .location(location)
                        .created(new Timestamp(System.currentTimeMillis()))
                .build()).getId();
    }
}
