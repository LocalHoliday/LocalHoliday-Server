package localholiday.spring.domain.bill;

import localholiday.spring.domain.entity.user.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {

    @Query(value = "select id from bill where user_id =:userId", nativeQuery = true)
    List<String> findAllByUserId(String userId);

}
