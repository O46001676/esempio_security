package esempio_security.esempio_security.services;

import esempio_security.esempio_security.dto.ToDoRequest;
import esempio_security.esempio_security.dto.ToDoRequestUpdate;
import esempio_security.esempio_security.dto.ToDoResponse;
import esempio_security.esempio_security.models.ToDoModel;
import esempio_security.esempio_security.models.UserModel;
import esempio_security.esempio_security.repositories.ToDoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;
    private final ModelMapper modelMapper;

    public ToDoService(ToDoRepository toDoRepository, ModelMapper modelMapper) {
        this.toDoRepository = toDoRepository;
        this.modelMapper = modelMapper;

    }

    public ToDoResponse addToDo(ToDoRequest toDo, UserModel user) {
        //ALTERNATIVA COMMENTATA
       /* ToDoModel toDoModel = new ToDoModel();
        toDoModel.setTodo(toDo.getTodo());
        toDoModel.setExpiryDate(toDo.getExpiryDate());
        toDoModel.setUserModel(user);*/

        ToDoModel todo2 = modelMapper.map(toDo, ToDoModel.class);
        todo2.setUserModel(user);
        todo2.setDone(false);
        ToDoModel added = this.toDoRepository.save(todo2);
        return modelMapper.map(added, ToDoResponse.class);
    }

    public int deleteByIdAndUserModel(Long id, UserModel user ){
        return this.toDoRepository.deleteByIdAndUserModel(id,user);
    }

    public List<ToDoResponse> getAllByUserModel(UserModel userModel) {
        List<ToDoModel> toDoModels = this.toDoRepository.getAllByUserModel(userModel);
        return toDoModels.stream().map(todo -> modelMapper.map(todo,ToDoResponse.class)).toList();
    }

    public ToDoResponse getToDoByIdAndUserModel(Long id, UserModel userModel){

        Optional<ToDoModel> toDoModel = this.toDoRepository.getToDoByIdAndUserModel(id,userModel);
        if(toDoModel.isEmpty()){
            throw new NullPointerException("ToDo non trovato");
        }
        return modelMapper.map(toDoModel.get(), ToDoResponse.class);
    }

    public ToDoResponse updateToDo(ToDoRequestUpdate toDo, UserModel userModel){
        ToDoModel todo =
                this.toDoRepository.getToDoByIdAndUserModel(toDo.getId(),userModel)
                        .orElseThrow(()->new NullPointerException("ToDo non trovato"));

        todo.setTodo(toDo.getTodo());
        todo.setExpiryDate(toDo.getExpiryDate());
        todo.setDone(toDo.isDone());
        ToDoModel todoUpdated = this.toDoRepository.save(todo);
        return modelMapper.map(todoUpdated, ToDoResponse.class);
    }

   public Page<ToDoResponse> getAllByUserModel(UserModel userModel, Pageable pageable){
       PageRequest pages = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
       Page<ToDoModel> todos = toDoRepository.getAllByUserModel(userModel,pages);
       return todos.map(todo->modelMapper.map(todo,ToDoResponse.class));

   }

}
