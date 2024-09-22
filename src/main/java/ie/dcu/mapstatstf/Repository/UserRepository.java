package ie.dcu.mapstatstf.Repository;

import ie.dcu.mapstatstf.Model.StatModel;
import ie.dcu.mapstatstf.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    List<UserModel> findAll();
}
