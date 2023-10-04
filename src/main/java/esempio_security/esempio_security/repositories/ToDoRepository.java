package esempio_security.esempio_security.repositories;

import esempio_security.esempio_security.models.ToDoModel;
import esempio_security.esempio_security.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoModel,Long> {

    void deleteByIdAndUserModel(Long id, UserModel userModel);

    Optional<ToDoModel> getToDoByIdAndUserModel(Long id, UserModel userModel);

    Iterable<ToDoModel> getAllByUserModel(UserModel userModel);

}
