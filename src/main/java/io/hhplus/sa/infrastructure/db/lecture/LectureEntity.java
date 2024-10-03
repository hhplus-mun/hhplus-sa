package io.hhplus.sa.infrastructure.db.lecture;

import io.hhplus.sa.domain.lecture.Lecture;
import io.hhplus.sa.domain.lecture.LectureCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "lecture")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;
    private String name;
    private String instructor;

    @Enumerated(EnumType.STRING)
    private LectureCategory category;

}
