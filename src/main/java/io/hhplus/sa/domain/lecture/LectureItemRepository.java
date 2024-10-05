package io.hhplus.sa.domain.lecture;

import java.time.LocalDate;
import java.util.List;

public interface LectureItemRepository {

    LectureItem save(LectureItem lectureItem);
    LectureItem findLectureItemByIdAndLectureIdWithPessimisticLock(long itemId, long lectureId);
    List<LectureItem> findOpenItemsByLectureDate(LocalDate requestDate);
}
