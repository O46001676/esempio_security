package esempio_security.esempio_security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ToDoRequestUpdate {

    private Long id;
    private String todo;

    private boolean done;

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
