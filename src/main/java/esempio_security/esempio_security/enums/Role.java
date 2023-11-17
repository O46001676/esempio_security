package esempio_security.esempio_security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static esempio_security.esempio_security.enums.Permission.*;

//Enum per definire i possibili ruoi degli utenti

@RequiredArgsConstructor
@AllArgsConstructor
public enum Role {
    USER(Set.of(USER_READ, USER_UPDATE)),
    ADMIN(Set.of(ADMIN_READ, ADMIN_UPDATE));


    @Getter
    @Setter
    private Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var auth = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        auth.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return auth;
    }
}
