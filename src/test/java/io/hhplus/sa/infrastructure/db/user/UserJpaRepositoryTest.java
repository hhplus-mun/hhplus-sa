package io.hhplus.sa.infrastructure.db.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserJpaRepositoryTest {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("[정상]: JPA 쿼리 조회 - 활성 사용자")
    void tdd() {
        //given
        long givenId = 1L;

        //when
        userJpaRepository.findActiveOneById(givenId);
    }
}