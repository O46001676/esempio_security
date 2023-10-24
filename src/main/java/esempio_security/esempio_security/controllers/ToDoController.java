package esempio_security.esempio_security.controllers;

import esempio_security.esempio_security.dto.ToDoRequest;
import esempio_security.esempio_security.dto.ToDoRequestUpdate;
import esempio_security.esempio_security.dto.ToDoResponse;
import esempio_security.esempio_security.models.ToDoModel;
import esempio_security.esempio_security.models.UserModel;
import esempio_security.esempio_security.services.ToDoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")

public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public ResponseEntity<?> getToDos(
            UsernamePasswordAuthenticationToken user,
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "expiryDate", direction = Sort.Direction.ASC)
            }
            ) Pageable pageable) {
        try {
            UserModel userModel = (UserModel) user.getPrincipal();
            Page<ToDoResponse> toDos = this.toDoService.getAllByUserModel(userModel,pageable);
            List<ToDoResponse> response = toDos.stream().toList();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /* @GetMapping("nopage")
    public ResponseEntity<?> getAllToDosNoPagination(UsernamePasswordAuthenticationToken user){

    }*/

    @PostMapping
    public ResponseEntity<?> addToDo(@Valid @RequestBody ToDoRequest toDo,
                                               UsernamePasswordAuthenticationToken user) {
        UserModel userModel = (UserModel) user.getPrincipal();
        try {
            ToDoResponse toDoAdded = this.toDoService.addToDo(toDo, userModel);
            return new ResponseEntity<>(toDoAdded, HttpStatus.CREATED);

        }catch (HttpMessageNotReadableException e){
            return new ResponseEntity<>("Data non valida", HttpStatus.I_AM_A_TEAPOT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeToDo(@Valid @PathVariable Long id, UsernamePasswordAuthenticationToken user) {
        UserModel userModel = (UserModel) user.getPrincipal();
        int nRowDeleted = this.toDoService.deleteByIdAndUserModel(id, userModel);
        if(nRowDeleted == 1){
            return new ResponseEntity<>("Todo eliminato con successo!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Todo non trovato!", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoResponse> getToDoByIdAndUserModel(@PathVariable Long id, UsernamePasswordAuthenticationToken user) {
        UserModel userModel = (UserModel) user.getPrincipal();
        ToDoResponse toDo = this.toDoService.getToDoByIdAndUserModel(id, userModel);
        return new ResponseEntity<>(toDo, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<?> updateToDo(@Valid @RequestBody ToDoRequestUpdate toDo,
                                                UsernamePasswordAuthenticationToken user) {
        try {
            UserModel userModel = (UserModel) user.getPrincipal();
            ToDoResponse toDoAdded = this.toDoService.updateToDo(toDo, userModel);
            return new ResponseEntity<>(toDoAdded, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
