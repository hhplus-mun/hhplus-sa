package io.hhplus.sa.domain.user;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    User getActiveUserById(Long id);
}
