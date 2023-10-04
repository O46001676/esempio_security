package esempio_security.esempio_security.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class ToDoModel {
    @Id
    @GeneratedValue
    private Long id;

    private String todo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    @ManyToOne
    private UserModel userModel;

    public ToDoModel() {
    }

    public ToDoModel(Long id, String todo, LocalDate expiryDate, UserModel userModel) {
        this.id = id;
        this.todo = todo;
        this.expiryDate = expiryDate;
        this.userModel = userModel;
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
