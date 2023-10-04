package esempio_security.esempio_security.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/") //mappatura generica
public class PageController {
    @GetMapping("/public") //indirizzamento con il /public
    public String publicPage(){
        return "Public page";
    }

    @GetMapping("/private") //indirizzamento con il /private
    public String privatePage(UsernamePasswordAuthenticationToken auth){
        return "Private Page";
    }
}
