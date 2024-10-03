package io.hhplus.sa.domain.lecture;

import java.util.List;

public interface LectureRepository {
    Lecture save(Lecture lecture);
    Lecture getById(Long id);
    List<Lecture> findAll();
}
