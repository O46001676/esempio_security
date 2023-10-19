package esempio_security.esempio_security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ToDoResponse {
    @NotBlank(message = "Todo obbligatorio")
    private String todo;
    @NotNull(message = "Inserire una data di nascita")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    private boolean done;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ToDoResponse() {
    }

    public ToDoResponse(String todo, LocalDate expiryDate, boolean done, int id) {
        this.todo = todo;
        this.expiryDate = expiryDate;
        this.done = done;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
