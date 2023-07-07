package localholiday.spring.domain.tour.tourBill;

import localholiday.spring.domain.entity.tour.TourSpotBill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class TourBillService {
    private final TourBillRepository tourBillRepository;

    @Transactional
    public String makeBill(String billId, String tourId){
        return tourBillRepository.save(TourSpotBill.builder()
                        .billId(billId)
                        .tourSpotId(tourId)
                        .created(new Timestamp(System.currentTimeMillis()))
                .build()).getId();
    }
}
