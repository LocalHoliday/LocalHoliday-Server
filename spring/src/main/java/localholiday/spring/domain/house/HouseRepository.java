package localholiday.spring.domain.house;

import localholiday.spring.domain.entity.house.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, String> {

    @Query(value = "select * from house where loc_code = (select id from location_code where code =:location)", nativeQuery = true)
    List<House> findALlByLocationCode(String location);

    Optional<House> findById(String Id);
}
