package io.hhplus.sa.domain.lecture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static io.hhplus.sa.domain.lecture.LectureCategory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LectureServiceUnitTest {

    @InjectMocks
    LectureService lectureService;

    @Mock
    LectureRepository lectureRepository;
    @Mock
    LectureItemRepository lectureItemRepository;

    @Test
    @DisplayName("[정상]: 특강 목록 조회기능 정상처리")
    void successList() {
        // given
        List<Lecture> lectures = Arrays.asList(
                new Lecture("TDD", "켄트 백", TDD),
                new Lecture("Spring", "토비", SPRING),
                new Lecture("jpa", "김영한", JPA)
        );

        given(lectureRepository.findAll())
                .willReturn(lectures);

        // when
        List<Lecture> lectureList = lectureService.getAllList();

        // then
        assertThat(lectureList.size()).isEqualTo(lectures.size());
    }

    @Test
    @DisplayName("[정상]: 특정 강의 항목 조회 기능 정상 처리")
    void lectureItem() {
        // given
        long lectureId = 1L;
        long itemId = 1L;
        Lecture lecture = new Lecture("TDD", "켄트백", TDD);
        LectureItem lectureItem = new LectureItem(lecture, LocalDate.now());
        ReflectionTestUtils.setField(lecture, "id", lectureId);
        ReflectionTestUtils.setField(lectureItem, "id", itemId);

        given(lectureItemRepository.findLectureItemByIdAndLectureIdWithPessimisticLock(lectureId, itemId))
                .willReturn(lectureItem);

        // when
        LectureItem foundItem = lectureService.getLectureItemForLecture(lectureId, itemId);

        assertThat(foundItem.getId()).isEqualTo(itemId);
        assertThat(foundItem.getLecture().getId()).isEqualTo(lectureId);
    }

    @Test
    @DisplayName("[예외]: lectureItem 이 lecture 하위에 존재하지 않을 경우")
    void lectureItemUnMatchingLecture() {
        // given
        long lectureId = 1L;
        long itemId = -1L;

        given(lectureItemRepository.findLectureItemByIdAndLectureIdWithPessimisticLock(itemId, lectureId))
                .willThrow(IllegalArgumentException.class);

        // when & then
        assertThatThrownBy(
                () -> lectureItemRepository.findLectureItemByIdAndLectureIdWithPessimisticLock(itemId, lectureId));
    }

    @Test
    @DisplayName("[정상]: 주어진 일자에 신청가능한 특강 목록 정상조회")
    void openLectureList() {
        // given
        String givenDate = "20240101";
        LocalDate localDate = LocalDate.parse(givenDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long userId = 1L;

        Lecture lecture = new Lecture("TDD", "켄트백", TDD);
        List<LectureItem> lectureItems = Arrays.asList(
                new LectureItem(lecture, LocalDate.now())
        );

        given(lectureItemRepository.findOpenItemsByLectureDate(localDate))
                .willReturn(lectureItems);

        // when
        List<LectureItem> itemList = lectureService.getOpenLectureItemList(givenDate, userId);

        // then
        assertThat(itemList.size()).isEqualTo(lectureItems.size());
    }
}