package localholiday.spring;

import localholiday.spring.entity.LocationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCodeRepository extends JpaRepository<LocationCode, Byte> {
    @Query(value = "select id from location_code where code=:location", nativeQuery = true)
    Byte convertCode2Id(String location);
}
