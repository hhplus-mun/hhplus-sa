package io.hhplus.sa.infrastructure.db.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u where u.id = :id and u.active = true")
    Optional<UserEntity> findActiveOneById(long id);
}
