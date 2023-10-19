package esempio_security.esempio_security.controllers;


import esempio_security.esempio_security.models.AuthenticationResponse;
import esempio_security.esempio_security.models.LoginModel;
import esempio_security.esempio_security.models.SignUpModel;
import esempio_security.esempio_security.services.AuthService;
import esempio_security.esempio_security.services.JwtService;
import esempio_security.esempio_security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthService authService, JwtService jwtService, UserService userService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    //indirizzamento login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginModel loginModel) {
        try {
            // Esegui l'autenticazione
            AuthenticationResponse authenticationResponse = this.authService.login(loginModel);

            if (authenticationResponse != null
                    && authenticationResponse.getToken() != null
                    && jwtService.isTokenValid(authenticationResponse.getToken(),
                    userService.loadUserByUsername(loginModel.getUsername()))) {
                // Se l'autenticazione ha successo e il token è generato, restituiscilo come parte della risposta.
                return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
            } else {
                // Altrimenti, restituisci una risposta di errore o vuota.
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //indirizzamento signup
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpModel signUpModel){
        try{
            return new ResponseEntity<>(this.authService.signUp(signUpModel), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
