package io.hhplus.sa.infrastructure.db.registration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RegistrationJpaRepositoryTest {
    @Autowired
    RegistrationJpaRepository registrationJpaRepository;

    @Test
    @DisplayName("[정상]: JPA - 이력 조회 쿼리 테스트")
    void historyQuery() {
        // given
        long userId = 1L;
        
        // when
        List<RegistrationEntity> registrations = registrationJpaRepository.findRegistrationHistoryByUserId(userId);

        assertThat(registrations.size()).isZero();
    }

    @Test
    @DisplayName("[정상]: JPA - 조회 쿼리 테스트")
    void registration() {
        long userId = 1L;
        long lectureItemId = 1L;

        registrationJpaRepository.countByUserIdAndLectureItemId(userId, lectureItemId);
    }
}