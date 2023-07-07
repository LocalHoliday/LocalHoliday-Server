package localholiday.spring.domain.job;

import localholiday.spring.domain.entity.job.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Optional<Job> hasUUID(String uuid){
        return jobRepository.findById(uuid);
    }
}
