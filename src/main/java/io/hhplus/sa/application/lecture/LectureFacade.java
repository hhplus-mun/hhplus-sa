package io.hhplus.sa.application.lecture;

import io.hhplus.sa.domain.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {
    private final LectureService lectureService;

    public List<LectureResult.Info> lectureList() {
        return lectureService.getAllList()
                .stream()
                .map(l -> new LectureResult.Info(
                        l.getId(),
                        l.getName(),
                        l.getInstructor(),
                        l.getCategory()
                )).toList();
    }

    public List<LectureResult.ShowAvailable> availableItemList(LectureCommand.ShowAvailable command) {

        return lectureService.getOpenLectureItemList(command.date(), command.userId())
                .stream()
                .map(i -> new LectureResult.ShowAvailable(
                        i.getLecture().getId(),
                        i.getLecture().getName(),
                        i.getLecture().getInstructor(),
                        i.getLecture().getCategory(),
                        i.getId(),
                        i.getLectureDate(),
                        i.getCapacity()
                )).toList();
    }
}
