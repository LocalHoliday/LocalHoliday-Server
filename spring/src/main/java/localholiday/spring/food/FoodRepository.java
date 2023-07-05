package localholiday.spring.food;

import localholiday.spring.entity.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, String> {

    @Query(value = "select * from food where loc_code = (select id from location_code where code =:location)", nativeQuery = true)
    List<Food> findALlByLocationCode(String location);

    Optional<Food> findById(String Id);
}
