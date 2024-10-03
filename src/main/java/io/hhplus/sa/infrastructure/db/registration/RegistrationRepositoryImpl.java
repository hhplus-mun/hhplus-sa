package io.hhplus.sa.infrastructure.db.registration;

import io.hhplus.sa.domain.registration.Registration;
import io.hhplus.sa.domain.registration.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegistrationRepositoryImpl implements RegistrationRepository {
    private final RegistrationJpaRepository registrationJpaRepository;

    @Override
    public Registration save(Registration registration) {
        RegistrationEntity save = registrationJpaRepository.save(registration.toEntity());

        return Registration.from(save);
    }

    @Override
    public List<Registration> findHistoryByUserId(Long userId) {
        return registrationJpaRepository.findRegistrationHistoryByUserId(userId)
                .stream()
                .map(Registration::from)
                .toList();
    }
}
