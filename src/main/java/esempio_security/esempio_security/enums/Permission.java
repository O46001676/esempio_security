package esempio_security.esempio_security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    USER_READ("user:read"),
    USER_UPDATE("user:update")
    ;

    @Getter
    private final String permission;
}
