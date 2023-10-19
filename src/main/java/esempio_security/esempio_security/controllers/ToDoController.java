package esempio_security.esempio_security.controllers;

import esempio_security.esempio_security.dto.ToDoRequest;
import esempio_security.esempio_security.dto.ToDoRequestUpdate;
import esempio_security.esempio_security.dto.ToDoResponse;
import esempio_security.esempio_security.models.ToDoModel;
import esempio_security.esempio_security.models.UserModel;
import esempio_security.esempio_security.services.ToDoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
    public ResponseEntity<Page<ToDoResponse>> getToDos(
            UsernamePasswordAuthenticationToken user,
            @PageableDefault(page = 0, size = 2)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "expiryDate", direction = Sort.Direction.DESC)
            }
            ) Pageable pageable) {
        try {
            UserModel userModel = (UserModel) user.getPrincipal();
            Page<ToDoResponse> toDos = this.toDoService.getAllByUserModel(userModel,pageable);
            return new ResponseEntity<>(toDos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addToDo(@RequestBody ToDoRequest toDo,
                                               UsernamePasswordAuthenticationToken user) {
        UserModel userModel = (UserModel) user.getPrincipal();
        try {
            ToDoResponse toDoAdded = this.toDoService.addToDo(toDo, userModel);
            return new ResponseEntity<>(toDoAdded, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ToDoModel> removeToDo(@PathVariable Long id, UsernamePasswordAuthenticationToken user) {
        UserModel userModel = (UserModel) user.getPrincipal();
        this.toDoService.deleteByIdAndUserModel(id, userModel);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoResponse> getToDoByIdAndUserModel(@PathVariable Long id, UsernamePasswordAuthenticationToken user) {
        UserModel userModel = (UserModel) user.getPrincipal();
        ToDoResponse toDo = this.toDoService.getToDoByIdAndUserModel(id, userModel);
        return new ResponseEntity<>(toDo, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<ToDoResponse> updateToDo(@RequestBody ToDoRequestUpdate toDo,
                                                UsernamePasswordAuthenticationToken user) {
        try {
            UserModel userModel = (UserModel) user.getPrincipal();
            ToDoResponse toDoAdded = this.toDoService.updateToDo(toDo, userModel);
            return new ResponseEntity<>(toDoAdded, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
