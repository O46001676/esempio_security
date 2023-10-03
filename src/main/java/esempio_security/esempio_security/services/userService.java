package esempio_security.esempio_security.services;

import esempio_security.esempio_security.models.UserModel;
import esempio_security.esempio_security.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userService implements UserDetailsService {
    private final UserRepository userRepository;

    public userService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username " +
                "not found"));
    }

    public UserModel saveUser(UserModel userModel){
        return this.userRepository.save(userModel);
    }
}
