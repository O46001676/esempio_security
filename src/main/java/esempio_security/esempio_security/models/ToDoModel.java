package esempio_security.esempio_security.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Length;

import java.lang.annotation.Target;
import java.time.LocalDate;
@Table(name = "Todos")
@Entity
public class ToDoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    @Length(min = 5, max = 500)
    @Lob //testi anche lunghi
    private String todo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    @Future(message = "La data di scadenza deve essere successiva a quella odierna")
    private LocalDate expiryDate;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserModel userModel;

    @Column
    private boolean done = false;
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
