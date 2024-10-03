package io.hhplus.sa.domain.registration;

import io.hhplus.sa.domain.exception.AlreadyRegisteredUserException;
import io.hhplus.sa.domain.exception.MaximumUserRegistrationException;
import io.hhplus.sa.domain.lecture.LectureItem;
import io.hhplus.sa.domain.lecture.LectureItemRepository;
import io.hhplus.sa.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final LectureItemRepository lectureItemRepository;

    /*
     * [요구사항 분석 및 정리]
     *
     * 특강에 신청자를 등록한다.
     * 신청자의 수강 목록에 특강을 추가한다.
     * 등록정보를 반환한다.
     *
     * 이미 강의에 등록된 이용자가 30명이라면 > MaximumUserRegistrationException
     */
    public Registration register(User user, LectureItem lecture) {
        if (lecture.getCapacity() == 0) throw new MaximumUserRegistrationException();
        if (isRegistered(user.getId(), lecture.getId())) throw new AlreadyRegisteredUserException("해당 특강을 이미 신청한 사용자입니다.");

        Registration registration = registrationRepository.save(new Registration(user, lecture));
        lecture.registerUser();
        lectureItemRepository.save(lecture);

        return registration;
    }

    protected boolean isRegistered(Long userId, Long lectureItemId) {
        return registrationRepository.countByUserIdAndLectureItemId(userId, lectureItemId) > 0
                ? true : false;
    }

    // 특강 ID 및 이름, 강연자 정보
    public List<Registration> history(long userId) {

        return registrationRepository.findHistoryByUserId(userId);
    }
}
