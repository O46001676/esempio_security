package esempio_security.esempio_security.models;

public class AuthenticationResponse {
    //contiene il token ed il suo costruttore/getter/setter
    String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
