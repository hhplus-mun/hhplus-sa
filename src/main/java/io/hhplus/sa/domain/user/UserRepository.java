package io.hhplus.sa.domain.user;

public interface UserRepository {
    User save(User user);
    User getActiveUserById(Long id);
}
