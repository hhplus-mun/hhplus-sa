package io.hhplus.sa.interfaces.api.lecture;

import io.hhplus.sa.application.lecture.LectureCommand;
import io.hhplus.sa.application.lecture.LectureFacade;
import io.hhplus.sa.application.registration.RegistrationCommand;
import io.hhplus.sa.application.registration.RegistrationFacade;
import io.hhplus.sa.application.registration.RegistrationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - 특강 신청 API
 * - 특강 신청 여부 조회 API
 */
@RestController
@RequestMapping("/v1/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final RegistrationFacade registrationFacade;
    private final LectureFacade lectureFacade;

    /**
     * 전체 특강 목록 조회 API
     */
    @GetMapping("/list")
    public List<LectureDto.LectureInfoResponse> list() {
        return lectureFacade.lectureList()
                .stream()
                .map(i -> new LectureDto.LectureInfoResponse(
                        i.id(),
                        i.name(),
                        i.instructor(),
                        i.category())
                ).toList();
    }

    /**
     * 2. 특강 선택 API
     * 날짜별 신청가능한 특강목록 조회 API
     * 
     * cf. 특강 신청 API는 하단에 위치
     */
    @GetMapping("/{date}/{userId}")
    public List<LectureDto.LectureItemResponse> itemListForDate(
            @PathVariable String date,
            @PathVariable Long userId
    ) {
        return lectureFacade.availableItemList(new LectureCommand.ShowAvailable(date, userId)).stream()
                .map(i -> new LectureDto.LectureItemResponse(
                        i.lectureId(),
                        i.name(),
                        i.instructor(),
                        i.category(),
                        i.lectureItemId(),
                        i.lectureDate(),
                        i.capacity()
                )).toList();
    }

    /**
     * 1. 특강 신청 API
     */
    @PostMapping("/{id}/{itemId}/registration/")
    public LectureDto.RegistrationResponse registration(
            @PathVariable("id") Long lectureId,
            @PathVariable("itemId") Long itemId,
            @RequestBody Long userId
    ) {
        RegistrationResult.Register result =
                registrationFacade.registerLecture(new RegistrationCommand.Register(lectureId, itemId, userId));

        return new LectureDto.RegistrationResponse(
                result.userId(),
                result.lectureId(),
                result.lectureName(),
                result.category(),
                result.lectureItemId(),
                result.isSuccess()
        );
    }

    /**
     * 3. 특강 신청 완료 목록 조회 API
     * 신청 완료 된 특강 목록을 조회하는 API
     */
    @GetMapping("/histories/{userId}")
    public List<LectureDto.RegistrationHistoryResponse> history(
            @PathVariable("userId") long id
    ) {
        return registrationFacade.getHistory(new RegistrationCommand.History(id))
                .stream()
                .map(h -> new LectureDto.RegistrationHistoryResponse(
                        h.lectureId(),
                        h.lectureName(),
                        h.instructor()
                )).toList();
    }
}
