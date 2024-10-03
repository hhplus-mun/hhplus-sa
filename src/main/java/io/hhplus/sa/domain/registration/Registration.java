package io.hhplus.sa.domain.registration;

import io.hhplus.sa.domain.lecture.LectureItem;
import io.hhplus.sa.domain.user.User;
import io.hhplus.sa.infrastructure.db.registration.RegistrationEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import static io.hhplus.sa.domain.exception.ExceptionMessage.ENTITY_IS_NULL;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Registration {
    private Long id;
    private User user;
    private LectureItem lectureItem;
    private LocalDateTime registeredAt;

    public Registration(User user, LectureItem lectureItem) {
        this.user = user;
        this.lectureItem = lectureItem;
        registeredAt = LocalDateTime.now();
    }

    public static Registration from(RegistrationEntity e) {
        if (e == null) throw new IllegalArgumentException(ENTITY_IS_NULL.message());

        return new Registration(
                e.getId(),
                User.from(e.getUser()),
                LectureItem.from(e.getLectureItem()),
                e.getRegisteredAt()
        );
    }

    public RegistrationEntity toEntity() {
        return new RegistrationEntity(id, user.toEntity(), lectureItem.toEntity(), registeredAt);
    }
}
