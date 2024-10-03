package io.hhplus.sa.infrastructure.db.lecture;

import io.hhplus.sa.domain.lecture.LectureItem;
import io.hhplus.sa.domain.lecture.LectureItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureItemRepositoryImpl implements LectureItemRepository {
    private final LectureItemJpaRepository lectureItemJpaRepository;

    @Override
    public LectureItem getByItemIdWithId(long lectureId, long itemId) {
        LectureItemEntity entity = lectureItemJpaRepository.findByItemIdWithId(lectureId, itemId)
                .orElseThrow(() -> new IllegalArgumentException());
        return LectureItem.from(entity);
    }

    @Override
    public List<LectureItem> findOpenItemsByLectureDate(LocalDate requestDate) {
        return lectureItemJpaRepository.findOpenItemsByLectureDate(requestDate)
                .stream()
                .map(LectureItem::from)
                .toList();
    }
}
