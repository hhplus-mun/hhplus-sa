package io.hhplus.sa.domain.lecture;

import java.time.LocalDate;
import java.util.List;

public interface LectureItemRepository {
    LectureItem getByItemIdWithId(long lectureId, long itemId);

    List<LectureItem> findOpenItemsByLectureDate(LocalDate requestDate);
}
