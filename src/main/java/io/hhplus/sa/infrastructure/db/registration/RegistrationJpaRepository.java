package io.hhplus.sa.infrastructure.db.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, Long> {

    @Query("select r from RegistrationEntity r join fetch r.lectureItem i join fetch i.lecture where r.user.id = :userId")
    List<RegistrationEntity> findRegistrationHistoryByUserId(Long userId);
}
