package io.hhplus.sa.infrastructure.db.lecture;

import io.hhplus.sa.domain.lecture.Lecture;
import io.hhplus.sa.domain.lecture.LectureCategory;
import io.hhplus.sa.domain.lecture.LectureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LectureRepositoryImplTest {

    @Autowired
    LectureRepository lectureRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("[정상]: 주어진 식별값을 통해 강의정보 정상 조회 확인")
    void get() {
        // given
        Lecture lecture = new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD);
        Lecture save = lectureRepository.save(lecture);
        Long givenId = save.getId();

        em.clear();

        // when
        Lecture foundLecture = lectureRepository.getById(givenId);

        // then
        assertThat(foundLecture.getId()).isEqualTo(givenId);
        assertThat(foundLecture.getName()).isEqualTo("TDD & CLEAN");
        assertThat(foundLecture.getInstructor()).isEqualTo("켄트백");
        assertThat(foundLecture.getCategory()).isEqualTo(LectureCategory.TDD);
    }

    @Test
    @DisplayName("[정상]: 모든 강의 목록 조회 기능 확인")
    void all() {
        // given
        int i;
        for (i=0; i<5; i++) { // i=5일 때 종료
            Lecture lecture = new Lecture(String.valueOf(i), "켄트백", LectureCategory.TDD);
            lectureRepository.save(lecture);
        }
        em.clear();

        // when
        List<Lecture> lectures = lectureRepository.findAll();
        lectures.forEach(System.out::println);

        // then
        assertThat(lectures.size()).isGreaterThan(0);
    }

}