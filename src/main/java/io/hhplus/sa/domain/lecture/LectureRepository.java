package io.hhplus.sa.domain.lecture;

import java.util.List;

public interface LectureRepository {
    Lecture getById(Long id);
    List<Lecture> findAll();
}
