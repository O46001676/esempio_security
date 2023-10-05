package esempio_security.esempio_security.models;

public class SignUpModel {
    //attributi, costruttore , getters e setters

    private String nome;
    private String username;
    private String password;

    private String cognome;
    private String email;

    public SignUpModel(String nome, String username, String password, String cognome, String email) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.cognome = cognome;
        this.email = email;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
