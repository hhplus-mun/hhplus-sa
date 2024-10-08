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

    public LectureItem getLectureItemForLecture(long itemId, long lectureId) {
        return lectureItemRepository.findLectureItemByIdAndLectureIdWithPessimisticLock(itemId, lectureId);
    }

    public List<LectureItem> getOpenLectureItemList(String date, long userId) {
        LocalDate requestDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));

        return lectureItemRepository.findOpenItemsByLectureDate(requestDate);
    }
}
