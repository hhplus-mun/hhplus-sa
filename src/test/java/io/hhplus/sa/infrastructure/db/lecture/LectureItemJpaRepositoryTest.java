package io.hhplus.sa.infrastructure.db.lecture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
class LectureItemJpaRepositoryTest {
    @Autowired
    LectureItemJpaRepository itemJpaRepository;

    @Test
    @DisplayName("[정상]: JPA 쿼리 조회 - 특정 일자에 열려있는 강의목록 조회")
    void queryMethodTest1() {
        // given
        LocalDate givenDate = LocalDate.now();

        // when
        itemJpaRepository.findOpenItemsByLectureDate(givenDate);
    }

    @Test
    @DisplayName("[정상]: JPA 쿼리 조회 - 특정 강의에 대한 상세 조회")
    void queryMethodTest2() {
        long lectureId = 1L;
        long lectureItemId = 1L;

        itemJpaRepository.findLectureItemByIdAndLectureId(lectureId, lectureItemId);
    }
}