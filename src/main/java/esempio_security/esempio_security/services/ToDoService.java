package esempio_security.esempio_security.services;

import esempio_security.esempio_security.dto.ToDoRequest;
import esempio_security.esempio_security.dto.ToDoRequestUpdate;
import esempio_security.esempio_security.models.ToDoModel;
import esempio_security.esempio_security.models.UserModel;
import esempio_security.esempio_security.repositories.ToDoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDoModel addToDo(ToDoRequest toDo, UserModel user) {
        ToDoModel toDoModel = new ToDoModel();
        toDoModel.setTodo(toDo.getTodo());
        toDoModel.setExpiryDate(toDo.getExpiryDate());
        toDoModel.setUserModel(user);
        return this.toDoRepository.save(toDoModel);
    }

    public void deleteByIdAndUserModel(Long id, UserModel user ){
        this.toDoRepository.deleteByIdAndUserModel(id,user);
    }

    public Iterable<ToDoModel> getAllByUserModel(UserModel userModel) {
        return this.toDoRepository.getAllByUserModel(userModel);
    }

    public Optional<ToDoModel> getToDoByIdAndUserModel(Long id, UserModel userModel){
        return this.toDoRepository.getToDoByIdAndUserModel(id, userModel);
    }

    public ToDoModel updateToDo(ToDoRequestUpdate toDo, UserModel userModel){
        Optional<ToDoModel> todos = getToDoByIdAndUserModel(toDo.getId(),userModel);
        if(todos.isEmpty()){
            throw new IllegalArgumentException("Errore, nessun todo trovato con questo id");
        }
        todos.get().setTodo(toDo.getTodo());
        todos.get().setExpiryDate(toDo.getExpiryDate());
        return this.toDoRepository.save(todos.get());
    }

   public Page<ToDoModel> getAllByUserModel(UserModel userModel, Pageable pageable){

       PageRequest pages = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
       Page<ToDoModel> todos = toDoRepository.getAllByUserModel(userModel,pages);
       return todos;
   }

}
