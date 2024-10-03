package io.hhplus.sa.infrastructure.db.registration;

import io.hhplus.sa.domain.lecture.*;
import io.hhplus.sa.domain.registration.Registration;
import io.hhplus.sa.domain.registration.RegistrationRepository;
import io.hhplus.sa.domain.user.User;
import io.hhplus.sa.domain.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
class RegistrationRepositoryImplTest {

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    LectureItemRepository lectureItemRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("[정상]: 정상적으로 특강 신청")
    void save() {
        // given
        User user = userRepository.save(new User("James"));
        Lecture lecture = new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD);
        Lecture savedLecture = lectureRepository.save(lecture);
        LectureItem lectureItem = lectureItemRepository.save(new LectureItem(savedLecture, LocalDate.now(), 30));

        em.flush();

        // when
        Registration save = registrationRepository.save(new Registration(user, lectureItem));

        // then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getRegisteredAt()).isNotNull();
    }

    @Test
    @DisplayName("[정상]: 사옹자가 신청한 특강목록 정상조회")
    void history() {
        // given
        User givenUser = userRepository.save(new User("James"));

        Lecture lecture = new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD);
        Lecture savedLecture = lectureRepository.save(lecture);
        LectureItem lectureItem = lectureItemRepository.save(new LectureItem(savedLecture, LocalDate.now(), 30));
        registrationRepository.save(new Registration(givenUser, lectureItem));
        em.flush();

        // when
        List<Registration> history = registrationRepository.findHistoryByUserId(givenUser.getId());

        // then
        assertThat(history.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("[정상]: 사용자의 특강 등록카운트 조회")
    void count() {
        // given
        User givenUser = userRepository.save(new User("James"));

        Lecture lecture = new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD);
        Lecture savedLecture = lectureRepository.save(lecture);
        LectureItem lectureItem = lectureItemRepository.save(new LectureItem(savedLecture, LocalDate.now(), 30));
        registrationRepository.save(new Registration(givenUser, lectureItem));

        // when
        Long count = registrationRepository.countByUserIdAndLectureItemId(givenUser.getId(), lectureItem.getId());

        // then
        assertThat(count).isGreaterThan(0);
    }
}