package io.hhplus.sa.domain.registration;

import java.util.List;

public interface RegistrationRepository {
    Registration save(Registration registration);
    List<Registration> findHistoryByUserId(Long lectureId);
}
