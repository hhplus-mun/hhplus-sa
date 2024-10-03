package io.hhplus.sa.domain.registration;

import io.hhplus.sa.domain.exception.MaximumUserRegistrationException;
import io.hhplus.sa.domain.lecture.Lecture;
import io.hhplus.sa.domain.lecture.LectureItem;
import io.hhplus.sa.domain.lecture.LectureItemRepository;
import io.hhplus.sa.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static io.hhplus.sa.domain.lecture.LectureCategory.TDD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceUnitTest {

    @InjectMocks
    RegistrationService registrationService;
    @Mock
    RegistrationRepository registrationRepository;
    @Mock
    LectureItemRepository lectureItemRepository;

    @Test
    @DisplayName("[정상]: 정상적으로 특강신청 완료")
    void success() {
        // given
        User user = new User("jae");
        Lecture lecture = new Lecture("TDD & 클린 아키텍처", "허재 코치", TDD);
        LectureItem lectureItem = new LectureItem(lecture, LocalDate.now(), 30);
        setField(user, "id", 1L);
        setField(lectureItem, "id", 1L);

        Registration registration = new Registration(user, lectureItem);
        setField(registration, "id", 1L);

        given(registrationRepository.save(any(Registration.class)))
                .willReturn(registration);
        given(lectureItemRepository.save(any(LectureItem.class)))
                .willReturn(lectureItem);

        // when
        Registration saved = registrationService.register(user, lectureItem);

        assertThat(saved.getId()).isNotZero();
        assertThat(saved.getUser().getId()).isEqualTo(1L);
        assertThat(saved.getLectureItem().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[예외]: 30명 정원 신청완료에 따른 예외 - MaximumUserRegistrationException")
    void maximumUserRegistration() {
        // given
        User user = new User("Kim");
        int zeroCapacity = 0;
        LectureItem lectureItem = new LectureItem(null, LocalDate.now(), zeroCapacity);

        // when & then
        assertThatThrownBy(() -> registrationService.register(user, lectureItem))
                .isInstanceOf(MaximumUserRegistrationException.class);
    }
    
    @Test
    @DisplayName("[정상]: 특강신청 히스토리 조회")
    void history() {
        // given
        long lectureId = 1L;
        Lecture tdd = new Lecture("TDD & 클린아키텍처", "켄트 백", TDD);
        setField(tdd, "id", lectureId);

        List<Registration> givenList = Arrays.asList(
                new Registration(new User("홍길동"), new LectureItem(tdd, LocalDate.now(), 30)),
                new Registration(new User("김철수"), new LectureItem(tdd, LocalDate.now(), 30)),
                new Registration(new User("고영희"), new LectureItem(tdd, LocalDate.now(), 30)),
                new Registration(new User("김민재"), new LectureItem(tdd, LocalDate.now(), 30))
        );
        given(registrationRepository.findHistoryByUserId(1L))
                .willReturn(givenList);

        // when
        List<Registration> history = registrationService.history(lectureId);

        // then
        assertThat(history.size()).isEqualTo(givenList.size());
        history.forEach(h -> System.out.println(
                String.format("%s: %d, %s, %s", h.getRegisteredAt(), h.getLectureItem().getId(), h.getLectureItem().getLecture().getName(), h.getLectureItem().getLecture().getCategory())
        ));
    }
}