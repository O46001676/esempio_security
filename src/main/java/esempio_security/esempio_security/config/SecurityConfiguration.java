package esempio_security.esempio_security.config;

import esempio_security.esempio_security.auth.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    //GLI ATTRIBUTI ED IL COSTRUTTORE SONO DA INSERIRE DOPO AVER REALIZZATO IL JwtAuthenticationFilter
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    //Creare il seguente bean e interromperlo ad un certo punto *
    //metodo che permette di defire quali permessi applicare in base all'indirizzo di riferimento
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeConfig->
                authorizeConfig.requestMatchers("/public")
                        .permitAll()
                        .requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/error")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
        )
                // PUNTO DI INTERRUZIONE DA CONTINUARE DOPO AVER REALIZZATO IL JwtAuthenticationFilter *

                //aggiungi il filtro creato (jwtAuthenticationFilter prima degli altri filtri) e verifica che i permessi
                //concessi siano giusti basandosi su username e password forniti
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                //questa configurazione indica a Spring Security di gestire l'autenticazione e l'autorizzazione in modo
                // "stateless", senza creare o gestire sessioni utente.
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //questa configurazione indica a Spring Security di utilizzare un provider di autenticazione specifico
                // (authenticationProvider) per gestire il processo di autenticazione all'interno dell'applicazione
                .authenticationProvider(authenticationProvider)

                //disabilita la protezione CSRF CSRF è una misura di sicurezza importante per prevenire attacchi che
                // sfruttano l'autenticazione dell'utente per compiere azioni non autorizzate
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}
