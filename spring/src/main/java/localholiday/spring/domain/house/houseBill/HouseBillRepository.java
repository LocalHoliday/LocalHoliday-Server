package localholiday.spring.domain.house.houseBill;

import localholiday.spring.domain.entity.house.HouseBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseBillRepository extends JpaRepository<HouseBill, String> {

    @Query(value = "select house_id from house_bill where bill_id =:billId", nativeQuery = true)
    String findHouseIdByBillId(String billId);

    Optional<HouseBill> findByBillIdAndHouseId(String billId, String houseId);
}
