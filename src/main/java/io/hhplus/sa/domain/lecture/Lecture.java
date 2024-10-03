package io.hhplus.sa.domain.lecture;

import io.hhplus.sa.infrastructure.db.lecture.LectureEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import static io.hhplus.sa.domain.exception.ExceptionMessage.ENTITY_IS_NULL;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Lecture {
    private Long id;
    private String name;
    private String instructor;
    private LectureCategory category;

    public Lecture(String name, String instructor, LectureCategory category) {
        this.name = name;
        this.instructor = instructor;
        this.category = category;
    }

    public static Lecture from(LectureEntity e) {
        if (e == null) throw new IllegalArgumentException(ENTITY_IS_NULL.message());

        return new Lecture(
                e.getId(),
                e.getName(),
                e.getInstructor(),
                e.getCategory()
        );
    }

    public LectureEntity toEntity() {
        return new LectureEntity(id, name, instructor, category);
    }
}
