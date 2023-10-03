package esempio_security.esempio_security.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            if(jwt.equals("password")){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(new JwAuthentication());
                SecurityContextHolder.setContext(securityContext);
                filterChain.doFilter(request,response);
                return;
            }
            else{
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().println("Non sei Salvo");
                return;
            }
        }

        filterChain.doFilter(request,response);
    }
}
