package esempio_security.esempio_security.controllers;


import esempio_security.esempio_security.models.AuthenticationResponse;
import esempio_security.esempio_security.models.LoginModel;
import esempio_security.esempio_security.models.SignUpModel;
import esempio_security.esempio_security.services.AuthService;
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

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //indirizzamento login
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginModel loginModel){
        try{
            return new ResponseEntity<>(this.authService.login(loginModel),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //indirizzamento signup
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignUpModel signUpModel){
        try{
            return new ResponseEntity<>(this.authService.signUp(signUpModel), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
