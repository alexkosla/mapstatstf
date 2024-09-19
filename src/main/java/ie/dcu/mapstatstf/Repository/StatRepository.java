package ie.dcu.mapstatstf.Repository;

import ie.dcu.mapstatstf.Model.StatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<StatModel, Integer> {
    List<StatModel> findAll();
}
