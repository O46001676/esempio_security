package esempio_security.esempio_security.controllers;

import esempio_security.esempio_security.dto.ToDoRequest;
import esempio_security.esempio_security.dto.ToDoRequestUpdate;
import esempio_security.esempio_security.models.ToDoModel;
import esempio_security.esempio_security.models.UserModel;
import esempio_security.esempio_security.services.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/todo")

public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<ToDoModel>> getToDos(UsernamePasswordAuthenticationToken user) {
        try {
            UserModel userModel = (UserModel) user.getPrincipal();
            Iterable<ToDoModel> toDos = this.toDoService.getAllByUserModel(userModel);
            return new ResponseEntity<>(toDos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ToDoModel> addToDo(@RequestBody ToDoRequest toDo, UsernamePasswordAuthenticationToken user) {
        UserModel userModel = (UserModel) user.getPrincipal();
        try {
            ToDoModel toDoAdded = this.toDoService.addToDo(toDo,userModel);
            return new ResponseEntity<>(toDoAdded, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ToDoModel> removeToDo(@PathVariable Long id, UsernamePasswordAuthenticationToken user){
        UserModel userModel = (UserModel) user.getPrincipal();
        this.toDoService.deleteByIdAndUserModel(id,userModel);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getToDoByIdAndUserModel(@PathVariable Long id, UsernamePasswordAuthenticationToken user){
        UserModel userModel = (UserModel) user.getPrincipal();
        Optional<ToDoModel> toDo = this.toDoService.getToDoByIdAndUserModel(id,userModel);
        if(toDo.isEmpty()){
            return new ResponseEntity<>("Non esiste!",HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(toDo.get(),HttpStatus.OK);
        }
    }

    //SISTEMARE
    @PutMapping
    public ResponseEntity<ToDoModel> updateToDo (@RequestBody ToDoRequestUpdate toDo,
                                                 UsernamePasswordAuthenticationToken user) {
        try {
            UserModel userModel = (UserModel) user.getPrincipal();
            ToDoModel toDoAdded = this.toDoService.updateToDo(toDo,userModel);
            return new ResponseEntity<>(toDoAdded, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
