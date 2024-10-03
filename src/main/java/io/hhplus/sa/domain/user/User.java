package io.hhplus.sa.domain.user;

import io.hhplus.sa.infrastructure.db.user.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static io.hhplus.sa.domain.exception.ExceptionMessage.ENTITY_IS_NULL;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Long id;
    private String name;
    private boolean active;

    public User(String name) {
        this.name = name;
    }

    public static User from(UserEntity e) {
        if (e == null) throw new IllegalArgumentException(ENTITY_IS_NULL.message());

        return new User(
                e.getId(),
                e.getName(),
                e.isActive()
        );
    }

    public UserEntity toEntity() {
        return new UserEntity(id, name, active);
    }
}
