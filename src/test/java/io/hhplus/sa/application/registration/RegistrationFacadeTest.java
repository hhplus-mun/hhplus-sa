package io.hhplus.sa.application.registration;

import io.hhplus.sa.domain.exception.AlreadyRegisteredUserException;
import io.hhplus.sa.domain.exception.MaximumUserRegistrationException;
import io.hhplus.sa.infrastructure.db.lecture.LectureItemEntity;
import io.hhplus.sa.infrastructure.db.lecture.LectureItemJpaRepository;
import io.hhplus.sa.infrastructure.db.user.UserEntity;
import io.hhplus.sa.infrastructure.db.user.UserJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    UserJpaRepository userJpaRepository;
    @Autowired
    LectureItemJpaRepository lectureItemJpaRepository;

    @PersistenceContext
    EntityManager em;

    /**
     * io.hhplus.sa.Dummy 클래스를 통해 사전 데이터 삽입
     */
    @Test
    @DisplayName("[정상]: 40명 신청 시, 30명만 성공")
    void step3() {
        // given
        int userNumber = 40;
        List<Long> userIds = userJpaRepository.findAll().stream()
                .filter(u -> u.getId() <= userNumber)
                .map(UserEntity::getId)
                .toList();

        LectureItemEntity entity = lectureItemJpaRepository.findAll().stream().findFirst().get();

        long lectureId = entity.getLecture().getId();
        long lectureItemId = entity.getId();

        em.flush();

        List<Runnable> tasks = new ArrayList<>();
        for (Long userId : userIds) {
            tasks.add(() -> {
                try {
                    registrationFacade.registerLecture(new RegistrationCommand.Register(lectureId, lectureItemId, userId));
                } catch (MaximumUserRegistrationException e) {
                    System.out.println("MaximumUserRegistrationException - userId: " + userId);
                }
            });
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

    /**
     * io.hhplus.sa.Dummy 클래스를 통해 사전 데이터 삽입
     */
    @Test
    @DisplayName("[정상]: 중복신청 - 5번 신청 시, 1번만 성공")
    void step4() {
        List<Long> ids = userJpaRepository.findAll().stream()
                .map(UserEntity::getId)
                .toList();
        Long userId = ids.get(ids.size() - 1);

        List<LectureItemEntity> list = lectureItemJpaRepository.findAll();
        LectureItemEntity entity = list.get(list.size() - 1);

        long lectureId = entity.getLecture().getId();
        long lectureItemId = entity.getId();

        em.flush();

        List<Runnable> tasks = new ArrayList<>();
        for (int i=0; i<5; i++) {
            tasks.add(() -> {
                try {
                    registrationFacade.registerLecture(new RegistrationCommand.Register(lectureId, lectureItemId, userId));
                } catch (AlreadyRegisteredUserException e) {
                }
            });
        }

        // when
        CompletableFuture allTask =
                CompletableFuture.allOf(
                        tasks.stream().map(task -> runAsync(task)).toArray(CompletableFuture[]::new)
                );
        allTask.join();

        // then
        int historyByUser = registrationFacade.getHistory(new RegistrationCommand.History(userId)).size();
        assertThat(historyByUser).isEqualTo(1);
    }
}