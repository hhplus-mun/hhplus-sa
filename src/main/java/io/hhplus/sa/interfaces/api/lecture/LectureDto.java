package io.hhplus.sa.interfaces.api.lecture;

import io.hhplus.sa.domain.lecture.LectureCategory;

import java.time.LocalDate;

public class LectureDto {
    public record RegistrationResponse (
            long userId,
            long lectureId,
            String lectureName,
            LectureCategory category,
            long lectureItemId,
            boolean isSuccess
    ) {}

    public record RegistrationHistoryResponse (
            long lectureId,
            String lectureName,
            String instructor
    ) {}

    public record LectureInfoResponse(
            long id,
            String name,
            String instructor,
            LectureCategory category
    ) {}

    public record LectureItemResponse(
            long lectureId,
            String name,
            String instructor,
            LectureCategory category,
            long lectureItemId,
            LocalDate lectureDate,
            int capacity
    ) {}
}
