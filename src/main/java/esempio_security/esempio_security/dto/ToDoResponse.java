package esempio_security.esempio_security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ToDoResponse {
    private String todo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    private boolean done;

    public ToDoResponse() {
    }

    public ToDoResponse(String todo, LocalDate expiryDate, boolean done) {
        this.todo = todo;
        this.expiryDate = expiryDate;
        this.done = done;
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
