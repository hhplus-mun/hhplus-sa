package io.hhplus.sa.infrastructure.db.lecture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
class LectureItemJpaRepositoryTest {
    @Autowired
    LectureItemJpaRepository itemJpaRepository;

    @Test
    void queryMethodTest1() {
        // given
        LocalDate givenDate = LocalDate.now();

        // when
        itemJpaRepository.findOpenItemsByLectureDate(givenDate);
    }

    @Test
    void queryMethodTest2() {
        long lectureId = 1L;
        long lectureItemId = 1L;

        itemJpaRepository.findByItemIdWithId(lectureId, lectureItemId);
    }
}