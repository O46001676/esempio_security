package esempio_security.esempio_security.repositories;

import esempio_security.esempio_security.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
    //aggiungere questo optional che restituisce una "scatola" che può o non può contenere un UserModel
    Optional<UserModel> findByUsername(String username);
}
