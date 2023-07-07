package localholiday.spring.domain.mainScreen.recommend;

import localholiday.spring.domain.entity.user.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, String> {

    List<Recommend> findAllByIdNotNull();
}
