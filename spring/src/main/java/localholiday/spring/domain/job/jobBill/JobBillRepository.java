package localholiday.spring.domain.job.jobBill;

import localholiday.spring.domain.entity.job.JobBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobBillRepository extends JpaRepository<JobBill, String> {
    Optional<JobBill> findByBillIdAndJobId(String billId, String jobId);
}
