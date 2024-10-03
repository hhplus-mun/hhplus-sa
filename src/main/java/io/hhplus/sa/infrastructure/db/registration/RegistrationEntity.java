package io.hhplus.sa.infrastructure.db.registration;

import io.hhplus.sa.domain.registration.Registration;
import io.hhplus.sa.infrastructure.db.lecture.LectureItemEntity;
import io.hhplus.sa.infrastructure.db.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "registration")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegistrationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private LectureItemEntity lectureItem;

    @Column(updatable = false)
    private LocalDateTime registeredAt;

//    public LectureDto.RegistrationResponse toResponse() {
//        return new LectureDto.RegistrationResponse(
//                id,
//                user.getId(),
//                lectureItem.getId(),
//                lectureItem.getLecture().getName(),
//                lectureItem.getLecture().getCategory()
//        );
//    }
//
//    public RegistrationInfo toInfo() {
//        return new RegistrationInfo(
//                id,
//                user.getId(),
//                lectureItem.getId(),
//                lectureItem.getLecture().getName(),
//                lectureItem.getLecture().getCategory()
//        );
//    }
}
