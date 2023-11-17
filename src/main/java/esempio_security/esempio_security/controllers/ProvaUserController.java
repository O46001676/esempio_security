package esempio_security.esempio_security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prova/user")
public class ProvaUserController {

    @GetMapping
    public String get(){
        return "GET:: prova user";
    }

    @PostMapping
    public String post(){
        return "POST:: prova user";
    }


}
