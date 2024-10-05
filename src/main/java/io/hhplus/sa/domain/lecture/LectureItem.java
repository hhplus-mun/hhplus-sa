package io.hhplus.sa.domain.lecture;

import io.hhplus.sa.infrastructure.db.lecture.LectureItemEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

import static io.hhplus.sa.domain.exception.ExceptionMessage.ENTITY_IS_NULL;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LectureItem {
    private static int DEFAULT_CAPACITY = 30;

    private Long id;
    private Lecture lecture;
    private LocalDate lectureDate;
    private int capacity;
    private int registrationCount;

    public LectureItem(Lecture lecture, LocalDate lectureDate, int capacity) {
        this.lecture = lecture;
        this.lectureDate = lectureDate;
        this.capacity = capacity;
    }

    public LectureItem(Lecture lecture, LocalDate lectureDate) {
        this(lecture, lectureDate, DEFAULT_CAPACITY);
    }

    public static LectureItem from(LectureItemEntity e) {
        if (e == null) throw new IllegalArgumentException(ENTITY_IS_NULL.message());

        return new LectureItem(
                e.getId(),
                Lecture.from(e.getLecture()),
                e.getLectureDate(),
                e.getCapacity(),
                e.getRegistrationCount()
        );
    }

    public LectureItemEntity toEntity() {
        return new LectureItemEntity(id, lecture.toEntity(), lectureDate, capacity, registrationCount);
    }

    public void registerUser() {
        registrationCount++;
        capacity--;
    }
}
