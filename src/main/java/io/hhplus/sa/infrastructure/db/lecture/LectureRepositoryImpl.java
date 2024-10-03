package io.hhplus.sa.infrastructure.db.lecture;

import io.hhplus.sa.domain.lecture.Lecture;
import io.hhplus.sa.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture save(Lecture lecture) {
        LectureEntity entity = lectureJpaRepository.save(lecture.toEntity());
        return Lecture.from(entity);
    }

    @Override
    public Lecture getById(Long id) {
        LectureEntity entity = lectureJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());

        return Lecture.from(entity);
    }

    @Override
    public List<Lecture> findAll() {
        return lectureJpaRepository.findAll()
                .stream()
                .map(Lecture::from)
                .toList();
    }
}
