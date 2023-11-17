package esempio_security.esempio_security;

import esempio_security.esempio_security.models.SignUpModel;
import esempio_security.esempio_security.services.AuthService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static esempio_security.esempio_security.enums.Role.*;

@SpringBootApplication
public class EsempioSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsempioSecurityApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(AuthService service){
		System.out.println("!!!AVVIO DELL'APP!!!");
		return args -> {
			var user = SignUpModel.builder()
					.nome("User")
					.cognome("User")
					.username("User1")
					.password("User123")
					.email("user@mail.com")
					.role(USER)
					.build();
			System.out.println(service.signUp(user).getToken());
		};
	}*/

}
