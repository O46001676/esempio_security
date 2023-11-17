package esempio_security.esempio_security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prova/admin")
public class ProvaAdminController {

    @GetMapping
    public String get(){
        return "GET:: prova admin";
    }

    @PostMapping
    public String post(){
        return "POST:: admin";
    }
}
