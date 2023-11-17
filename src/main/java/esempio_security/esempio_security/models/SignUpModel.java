package esempio_security.esempio_security.models;

import esempio_security.esempio_security.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpModel {
    //attributi, costruttore , getters e setters

    private String nome;
    private String username;
    private String password;
    private String cognome;
    private String email;
    private Role role;

}
