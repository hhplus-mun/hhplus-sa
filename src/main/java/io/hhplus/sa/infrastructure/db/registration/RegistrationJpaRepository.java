package io.hhplus.sa.infrastructure.db.registration;

import io.hhplus.sa.domain.registration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, Long> {

    @Query("select r from RegistrationEntity r " +
            "join fetch r.lectureItem i join fetch i.lecture where r.user.id = :userId")
    List<RegistrationEntity> findRegistrationHistoryByUserId(Long userId);

    @Query("select count(r) from RegistrationEntity  r " +
            "join r.user u where r.user.id = :userId and r.lectureItem.id = :lectureItemId")
    Long countByUserIdAndLectureItemId(@Param("userId") Long userId, @Param("lectureItemId") Long lectureItemId);
}
