package esempio_security.esempio_security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PageController {
    @GetMapping("/public")
    public String publicPage(){
        return "Public page";
    }

    @GetMapping("/private")
    public String privatePage(){
        return "Private Page";
    }
}
