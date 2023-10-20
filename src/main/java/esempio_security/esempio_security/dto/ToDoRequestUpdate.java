package esempio_security.esempio_security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ToDoRequestUpdate {
    @NotBlank(message = "ID obbligatorio")
    private Long id;
    @NotBlank(message = "Todo obbligatorio")
    private String todo;
    private boolean done;
    @NotNull(message = "Inserire una data di nascita")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    public ToDoRequestUpdate(Long id, String todo, boolean done, LocalDate expiryDate) {
        this.id = id;
        this.todo = todo;
        this.done = done;
        this.expiryDate = expiryDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
