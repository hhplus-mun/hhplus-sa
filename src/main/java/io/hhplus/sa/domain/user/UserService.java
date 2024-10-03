package io.hhplus.sa.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getActiveUser(Long userId) {
        return userRepository.getActiveUserById(userId);
    }
}
