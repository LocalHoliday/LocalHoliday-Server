package localholiday.spring.domain.job.jobReview;

import localholiday.spring.domain.entity.job.JobReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobReviewRepository extends JpaRepository<JobReview, String> {
}
