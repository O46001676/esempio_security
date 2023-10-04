package esempio_security.esempio_security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static javax.crypto.Cipher.SECRET_KEY;
//MANIPOLAZIONE DEI DATI NEL JWT TOKEN
@Service
public class JwtService {
    //Aggiungiamo come attributo la secret key e ne facciamo il costruttore
    private final String Secret_key;

    public JwtService(@Value("${SECRET_KEY}")String secret_key) {
        Secret_key = secret_key;
    }

    //permette di ottenere la secretkey decodificata come meglio vogliamo (solitamente BASE64)
    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(Secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //questa funzione prende un token JWT, ne verifica la firma utilizzando una chiave di firma specificata,
    //lo analizza e restituisce tutte le dichiarazioni contenute nel token. Queste dichiarazioni possono
    //includere informazioni sull'utente, i ruoli, i diritti di accesso o qualsiasi altra informazione utile
    //necessaria
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //questa funzione permette di estrarre una specifica claim da un token JWT in modo generico, consentendo di
    // specificare quale claim estrarre utilizzando una funzione di risoluzione delle dichiarazioni
    // (claimsResolver). Questo approccio modulare rende la funzione flessibile e adatta a diverse situazioni
    // in cui è necessario recuperare informazioni specifiche dal token JWT.
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //questa funzione semplifica l'estrazione del nome utente dal token JWT, utilizzando una funzione
    //di utilità generica (extractClaim) per ottenere il "subject" dal token JWT.
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    //questa funzione semplifica l'estrazione della data di scadenza dal token JWT, utilizzando una funzione di
    // utilità generica (extractClaim) per ottenere la data di scadenza dal token JWT.
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    //In sintesi, la funzione isTokenValid verifica se un token JWT è valido confrontando il nome utente estratto dal
    // token con il nome utente fornito negli UserDetails e verificando che il token non sia scaduto. Se entrambe le
    // condizioni sono soddisfatte, il token è considerato valido per l'utente specificato, e la funzione restituisce
    // true, altrimenti restituisce false.
    public boolean isTokenValid (String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //la funzione isTokenExpired verifica se il token JWT fornito come input è scaduto o meno. Restituisce true se
    // il token è scaduto (la data di scadenza è precedente alla data attuale) e false se il token è ancora valido.
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // la funzione generateToken crea un nuovo token JWT con le dichiarazioni specificate (compresi dettagli
    // dell'utente e dichiarazioni extra), una data di emissione, una data di scadenza e una firma digitale.
    // Questo token JWT viene quindi restituito come una stringa.
    public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //questa funzione generateToken è un metodo di comodo che semplifica la generazione di un token JWT basato solo
    // sui dettagli dell'utente forniti in UserDetails, senza specifiche dichiarazioni extra personalizzate.
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

}
