package esempio_security.esempio_security.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;

import java.lang.annotation.Target;
import java.time.LocalDate;
@Table(name = "Todos")
@Entity
public class ToDoModel {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Size(min = 2)
    private String todo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserModel userModel;

    @Column
    private boolean done;
    public ToDoModel() {
    }

    public ToDoModel(Long id, String todo, LocalDate expiryDate, UserModel userModel, boolean done) {
        this.id = id;
        this.todo = todo;
        this.expiryDate = expiryDate;
        this.userModel = userModel;
        this.done = done;
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

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
