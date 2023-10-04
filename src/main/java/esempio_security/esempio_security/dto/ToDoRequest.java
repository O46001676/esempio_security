package esempio_security.esempio_security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import esempio_security.esempio_security.models.ToDoModel;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class ToDoRequest {
    private String todo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    public ToDoRequest() {
    }

    public ToDoRequest(String todo, LocalDate expiryDate) {
        this.todo = todo;
        this.expiryDate = expiryDate;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
