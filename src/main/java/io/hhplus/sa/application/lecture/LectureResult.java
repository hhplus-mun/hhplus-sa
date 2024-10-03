package io.hhplus.sa.application.lecture;

import io.hhplus.sa.domain.lecture.LectureCategory;

import java.time.LocalDate;

public class LectureResult {
    public record ShowAvailable (
            long lectureId,
            String name,
            String instructor,
            LectureCategory category,
            long lectureItemId,
            LocalDate lectureDate,
            int capacity
    ) {}

    public record Info (
            long id,
            String name,
            String instructor,
            LectureCategory category
    ) {}
}
