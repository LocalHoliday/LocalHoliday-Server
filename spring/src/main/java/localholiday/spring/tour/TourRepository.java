package localholiday.spring.tour;


import localholiday.spring.entity.tour.TourSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<TourSpot, String> {

    @Query(value = "select * from tour_spot where loc_code = (select id from location_code where code =:location)", nativeQuery = true)
    List<TourSpot> findALlByLocationCode(String location);
}
