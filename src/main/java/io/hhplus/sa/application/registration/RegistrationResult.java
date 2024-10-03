package io.hhplus.sa.application.registration;

import io.hhplus.sa.domain.lecture.LectureCategory;

public class RegistrationResult {
    public record Register (
            long userId,
            long lectureId,
            String lectureName,
            LectureCategory category,
            long lectureItemId,
            boolean isSuccess
    ) {}

    public record History(
            long lectureId,
            String lectureName,
            String instructor
    ) {}
}

