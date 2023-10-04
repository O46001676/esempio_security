package esempio_security.esempio_security.auth;

import esempio_security.esempio_security.services.JwtService;
import esempio_security.esempio_security.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //GLI ATTRIBUTI CON IL COSTRUTTORE VANNO INSERITI DOPO AVER REALIZZATO IL JwtService ED L'UserService
   private final JwtService jwtService;
   private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    //metodo implementato obbligatoriamente e modificato manualmente
    //metodo per gestire l'autenticazione degli utenti utilizzando token JWT (JSON Web Token)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //attributi
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        //Verifica se un token JWT non è presente  di una richiesta o se non inizia con Bearer
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            //allora passa al prossimo filtro
            filterChain.doFilter(request,response);
            return;
        }

        //assegna a jwt il token senza la scritta Bearer%
        jwt = authHeader.substring(7);

        //DA CONTINUARE DOPO AVER REALIZZATO IL JwtService

        //assegno ad username l'username prelevandolo da dentro il token
        username= jwtService.extractUsername(jwt);

        //se l'username non è nullo e non è autenticato
        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){

            //crea un nuovo utente e gli assegna tutti i dati prelevati dal database che corrispondo a quell'username
            UserDetails userDetails = this.userService.loadUserByUsername(username);

            //se il token è valido
            if(jwtService.isTokenValid(jwt,userDetails)){
                //crea un contenitore vuoto per contenere le informazioni riguardo l'autenticazione e le autorizzazioni
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                //questa linea di codice crea un oggetto UsernamePasswordAuthenticationToken che rappresenta una
                // richiesta di autenticazione basata su nome utente e password, utilizzando i dettagli dell'utente
                // forniti in userDetails.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                //inserisce i dati all'interno del contenitore securityContext
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
                }
            }
        //Se gli if non sono verificati o al termine della loro esecuzione passa ai prossimi filtri
        filterChain.doFilter(request,response);
    }
}
