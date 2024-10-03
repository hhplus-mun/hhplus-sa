package io.hhplus.sa.domain.registration;

import io.hhplus.sa.infrastructure.db.registration.RegistrationEntity;

import java.util.List;

public interface RegistrationRepository {
    Registration save(Registration registration);
    long countByLectureId(Long lectureId);
    List<Registration> findHistoryByUserId(Long lectureId);
}
