package esempio_security.esempio_security.services;

import esempio_security.esempio_security.enums.Role;
import esempio_security.esempio_security.exc.DatiNonValidiException;
import esempio_security.esempio_security.models.AuthenticationResponse;
import esempio_security.esempio_security.models.LoginModel;
import esempio_security.esempio_security.models.SignUpModel;
import esempio_security.esempio_security.models.UserModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final esempio_security.esempio_security.services.UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder, esempio_security.esempio_security.services.UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public AuthenticationResponse login(LoginModel loginModel){
        UserDetails userFoundByEmail = userService.findByEmail(loginModel.getEmail());

        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userFoundByEmail.getUsername(),
                        loginModel.getPassword()
                )
        );
        UserDetails user = this.userService.loadUserByUsername(userFoundByEmail.getUsername());
        String jwtToken = this.jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse signUp(SignUpModel signUpModel){
        UserModel userModel = new UserModel();
        userModel.setName(signUpModel.getNome());
        userModel.setUsername(signUpModel.getUsername());
        userModel.setPassword(passwordEncoder.encode(signUpModel.getPassword()));
        userModel.setCognome(signUpModel.getCognome());
        userModel.setEmail(signUpModel.getEmail());
        userModel.setRole(Role.USER);
        UserModel userNew;

        try{
            userNew = this.userService.saveUser(userModel);
        }catch(DataIntegrityViolationException ex){
            throw new DatiNonValidiException("Dati già presenti nel database");
        }

        if(userNew == null){
            throw new DatiNonValidiException("User non inserito!");
        }
        String jwtToken = this.jwtService.generateToken(userNew);
        return new AuthenticationResponse(jwtToken);
    }
}
