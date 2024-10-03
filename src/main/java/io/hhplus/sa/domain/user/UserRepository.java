package io.hhplus.sa.domain.user;

import java.util.Optional;

public interface UserRepository {
    User getActiveUserById(Long id);
}
