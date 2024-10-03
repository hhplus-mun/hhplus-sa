package io.hhplus.sa.application.lecture;

public class LectureCommand {
    /** LectureItemList */
    public record ShowAvailable(
            String date,
            long userId
    ) {}
}
