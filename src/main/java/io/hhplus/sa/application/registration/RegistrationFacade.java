package io.hhplus.sa.application.registration;

import io.hhplus.sa.domain.lecture.LectureItem;
import io.hhplus.sa.domain.registration.Registration;
import io.hhplus.sa.domain.user.User;
import io.hhplus.sa.domain.lecture.LectureService;
import io.hhplus.sa.domain.registration.RegistrationService;
import io.hhplus.sa.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegistrationFacade {
    private final UserService userService;
    private final LectureService lectureService;
    private final RegistrationService registrationService;

    @Transactional
    public RegistrationResult.Register registerLecture(RegistrationCommand.Register command) {
        User activeUser = userService.getActiveUser(command.userId());
        LectureItem lectureItem = lectureService.getLectureItemForLecture(command.lectureItemId(), command.lectureId());

        Registration registration = registrationService.register(activeUser, lectureItem);
        return new RegistrationResult.Register(
                registration.getUser().getId(),
                registration.getLectureItem().getLecture().getId(),
                registration.getLectureItem().getLecture().getName(),
                registration.getLectureItem().getLecture().getCategory(),
                registration.getLectureItem().getId(),
                true
        );
    }

    public List<RegistrationResult.History> getHistory(RegistrationCommand.History command) {

        return registrationService.history(command.userId())
                .stream()
                .map(h -> new RegistrationResult.History(
                        h.getLectureItem().getLecture().getId(),
                        h.getLectureItem().getLecture().getName(),
                        h.getLectureItem().getLecture().getInstructor()
                )).toList();
    }
}
