package io.hhplus.sa.application.registration;

import io.hhplus.sa.domain.lecture.*;
import io.hhplus.sa.domain.user.User;
import io.hhplus.sa.domain.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.runAsync;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RegistrationFacadeTest {

    @Autowired
    RegistrationFacade registrationFacade;

    @Autowired
    UserRepository userRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    LectureItemRepository lectureItemRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("[정상]: 40명 신청 시, 30명만 성공")
    @Rollback(value = false)
    void step3() {
        // given
        int userNumber = 40;
        List<Long> userIds = getUserIdList(userNumber);

        Pair idPair = getLectureIdPair();
        long lectureId = idPair.lectureId;
        long lectureItemId = idPair.lectureItemId;

        em.flush();

        List<Runnable> tasks = new ArrayList<>();
        for (Long userId : userIds) {
            tasks.add(() ->
                    registrationFacade.registerLecture(new RegistrationCommand.Register(lectureId, lectureItemId, userId)));
        }

        // when
        CompletableFuture allTask =
                CompletableFuture.allOf(
                        tasks.stream().map(task -> runAsync(task)).toArray(CompletableFuture[]::new)
                );
        allTask.join();

        // then
        int historyCnt = 0;
        for (Long userId : userIds) {
            int historyPerUserId = registrationFacade.getHistory(new RegistrationCommand.History(userId)).size();

            historyCnt += historyPerUserId;
        }

        assertThat(historyCnt).isEqualTo(30);
    }

    private Pair getLectureIdPair() {
        Lecture lecture = lectureRepository.save(
                new Lecture("TDD & CLEAN", "켄트백", LectureCategory.TDD)
        );
        LectureItem lectureItem = lectureItemRepository.save(new LectureItem(lecture, LocalDate.now()));

        return new Pair(lecture.getId(), lectureItem.getId());
    }

    private List<Long> getUserIdList(int n) {
        List<Long> ids = new ArrayList<>();

        for (int i=1; i<=n; i++) {
            User user = userRepository.save(new User("t" + i));
            ids.add(user.getId());
        }

        return ids;
    }

    public record Pair(
            long lectureId,
            long lectureItemId
    ) {}
}