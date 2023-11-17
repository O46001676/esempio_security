package esempio_security.esempio_security.models;
import esempio_security.esempio_security.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.mapping.Constraint;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

//una classe che implementa l'interfaccia UserDetails ereditando diversi metodi
@Entity
@Table(name = "Users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements UserDetails {
    //attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    @Length(min = 2, max = 19, message = "Il username deve essere compreso tra 2 e 19 caratteri")
    @NotBlank
    private String username;

    @Column
    @NotBlank(message = "Il campo deve essere necessariamente riempito")
    private String password;
    @Column
    @Length(min = 2, max = 19, message = "Il nome deve essere compreso tra 2 e 19 caratteri")
    @NotBlank
    private String name;

    @Column
    @Length(min = 2, max = 19, message = "Il cognome deve essere compreso tra 2 e 19 caratteri")
    @NotBlank
    private String cognome;

    @Column(unique = true)
    @Email(message = "Il campo email deve essere una email")
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING) //enum
    private Role role;


    //settare i seguenti metodi ereditati a return true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //cambiare il tipo di return per restituire una lista di ruoli
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

}
