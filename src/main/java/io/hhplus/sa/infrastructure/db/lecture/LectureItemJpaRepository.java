package io.hhplus.sa.infrastructure.db.lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LectureItemJpaRepository extends JpaRepository<LectureItemEntity, Long> {

    @Query("select i from LectureItemEntity i " +
            "join fetch i.lecture l " +
            "where i.lectureDate = :requestDate and i.capacity > 0")
    List<LectureItemEntity> findOpenItemsByLectureDate(@Param("requestDate") LocalDate requestDate);

    @Query("select i from LectureItemEntity i " +
            "join fetch i.lecture l " +
            "where i.id = :itemId and i.lecture.id = :lectureId")
    Optional<LectureItemEntity> findLectureItemByIdAndLectureId(@Param("itemId") long itemId, @Param("lectureId") long lectureId);
}
