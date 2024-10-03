package io.hhplus.sa.infrastructure.db.user;

import io.hhplus.sa.domain.exception.ActiveUserException;
import io.hhplus.sa.domain.user.User;
import io.hhplus.sa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity entity = userJpaRepository.save(user.toEntity());
        return User.from(entity);
    }

    @Override
    public User getActiveUserById(Long id) {
        UserEntity entity = userJpaRepository.findActiveOneById(id)
                .orElseThrow(() -> new ActiveUserException("유효한 사용자가 조회되지 않습니다."));

        return User.from(entity);
    }
}
