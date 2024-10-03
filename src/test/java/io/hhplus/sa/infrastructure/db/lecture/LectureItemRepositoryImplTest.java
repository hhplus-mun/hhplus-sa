package io.hhplus.sa.infrastructure.db.lecture;

import io.hhplus.sa.domain.lecture.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LectureItemRepositoryImplTest {

    @Autowired
    LectureItemRepository lectureItemRepository;

    // ===================================================
    @Autowired
    LectureRepository lectureRepository;
    @PersistenceContext
    EntityManager em;
    // ===================================================

    @Test
    @DisplayName("[정상]: 특정 특강 조회 시 정상조회")
    void success() {
        // given
        Lecture lecture = new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD);
        Lecture savedLecture = lectureRepository.save(lecture);

        LocalDate lectureDate = LocalDate.now();
        LectureItem lectureItem = new LectureItem(savedLecture, lectureDate);
        LectureItem savedLectureItem = lectureItemRepository.save(lectureItem);

        em.flush();

        // when
        LectureItem foundItem = lectureItemRepository.findLectureItemByIdAndLectureId(savedLectureItem.getId(), savedLecture.getId());

        // then
        assertThat(foundItem.getLecture().getName()).isEqualTo(lecture.getName());
        assertThat(foundItem.getLectureDate()).isEqualTo(lectureDate);
    }

    @Test
    @DisplayName("[정상]: 특정 일자에 신청가능한 특강목록 조회기능 정상동작")
    void successOpenItems() {
        // given - 가능한 특강일 자 중 하루는 정원이 가득찼다.
        Lecture lecture = new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD);
        Lecture savedLecture = lectureRepository.save(lecture);

        LocalDate givenDate = LocalDate.of(1950, 6, 25);
        LectureItem lectureItem = new LectureItem(savedLecture, givenDate);
        LectureItem lectureItem1 = new LectureItem(savedLecture, givenDate, 0);
        LectureItem lectureItem2 = new LectureItem(savedLecture, givenDate, 3);
        LectureItem lectureItem3 = new LectureItem(savedLecture, givenDate, 5);
        lectureItemRepository.save(lectureItem);
        lectureItemRepository.save(lectureItem1);
        lectureItemRepository.save(lectureItem2);
        lectureItemRepository.save(lectureItem3);

        // when
        List<LectureItem> lectures = lectureItemRepository.findOpenItemsByLectureDate(givenDate);

        // then - 가능한 특강일 자 중 하루는 정원이 가득찼다.
        assertThat(lectures.size()).isEqualTo(3);
    }
}