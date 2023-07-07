package localholiday.spring.domain.job.jobBill;

import localholiday.spring.domain.entity.job.JobBill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class JobBillService {

    private final JobBillRepository jobBillRepository;

    @Transactional
    public String makeBill(String billId, String jobId){
        return jobBillRepository.save(JobBill.builder()
                        .billId(billId)
                        .jobId(jobId)
                        .created(new Timestamp(System.currentTimeMillis()))
                .build()).getId();
    }
}
