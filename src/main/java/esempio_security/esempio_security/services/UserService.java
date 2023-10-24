package esempio_security.esempio_security.services;

import esempio_security.esempio_security.exc.DatiNonValidiException;
import esempio_security.esempio_security.models.UserModel;
import esempio_security.esempio_security.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//implementa un'interfaccia appartenente al framework della security
@Service
public class UserService implements UserDetailsService {
    //attributo di dipendenza da UserRepository con costruttore
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //metodo ereditato dall'implementazione da dover modificare il tipo di ritorno
    //restituisce un utente andando a controllare nel database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username " +
                "not found"));
    }

    //metodo per salvare un utente all'interno del database
    public UserModel saveUser(UserModel userModel){
        return this.userRepository.save(userModel);
    }

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new DatiNonValidiException("User non trovato!"));
    }
}
