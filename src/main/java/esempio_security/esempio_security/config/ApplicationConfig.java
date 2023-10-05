package esempio_security.esempio_security.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    //Creo la dipendenza con UserService e faccio il costruttore
    private final esempio_security.esempio_security.services.UserService UserService;

    public ApplicationConfig(esempio_security.esempio_security.services.UserService UserService) {
        this.UserService = UserService;
    }

    //Creo un Bean authehticationProvider
    // In sintesi, questo codice configura un bean di autenticazione personalizzato basato su
    // DaoAuthenticationProvider, imposta un UserDetailsService e un PasswordEncoder specifici e lo
    // rende disponibile all'interno dell'applicazione per gestire l'autenticazione degli utenti.
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(UserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    //questo codice configura un bean di Spring che fornisce un oggetto PasswordEncoder basato sull'algoritmo
    // di hashing bcrypt. Questo bean può essere utilizzato all'interno dell'applicazione per codificare
    // in modo sicuro le password degli utenti prima di memorizzarle o per verificare la corrispondenza
    // delle password durante l'autenticazione.
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //In sintesi, questo codice configura un bean AuthenticationManager all'interno dell'applicazione
    //Spring Security utilizzando la configurazione fornita come argomento. L'AuthenticationManager
    //configurato sarà utilizzato per gestire le operazioni di autenticazione degli utenti all'interno
    //dell'applicazione.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    };

}
