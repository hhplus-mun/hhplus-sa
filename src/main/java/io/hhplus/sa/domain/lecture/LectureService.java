package io.hhplus.sa.domain.lecture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LectureItemRepository lectureItemRepository;

    public List<Lecture> getAllList() {
        return lectureRepository.findAll();
    }

    public Lecture getLecture(Long lectureId) {
        return lectureRepository.getById(lectureId);
    }

    public LectureItem getLectureItem(long lectureId, long itemId) {
        return lectureItemRepository.getByItemIdWithId(lectureId, itemId);
    }

    public List<LectureItem> getOpenLectureItemList(String date, long userId) {
        LocalDate requestDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));

        return lectureItemRepository.findOpenItemsByLectureDate(requestDate);
    }
}
