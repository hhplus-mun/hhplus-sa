package io.hhplus.sa;

import io.hhplus.sa.domain.lecture.*;
import io.hhplus.sa.domain.user.User;
import io.hhplus.sa.domain.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Dummy {

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final LectureItemRepository lectureItemRepository;


    @PostConstruct
    void init() {
        for (int i=0; i<50; i++) {
            userRepository.save(new User("t" + i));
        }
        Lecture lecture = lectureRepository.save(new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD));
        lectureItemRepository.save(new LectureItem(lecture, LocalDate.now()));

        Lecture lecture2 = lectureRepository.save(new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD));
        lectureItemRepository.save(new LectureItem(lecture2, LocalDate.now()));
    }
}
