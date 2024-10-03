package io.hhplus.sa.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("[정상] 활성 사용자 정상 조회")
    void activeUser() {
        // given
        long id = 1L;
        User kim = new User("kim");
        ReflectionTestUtils.setField(kim, "id", id);

        given(userRepository.getActiveUserById(id))
                .willReturn(kim);

        // when
        User activeUser = userService.getActiveUser(id);

        assertThat(activeUser.getId()).isEqualTo(id);
        assertThat(activeUser.getName()).isEqualTo("kim");
        assertThat(activeUser.isActive()).isTrue();
    }

    @Test
    @DisplayName("[예외]: 활성 사용자 조회 실패")
    void failActiveUser() {
        // given
        long id = -1L;
        given(userRepository.getActiveUserById(id))
                .willThrow(IllegalArgumentException.class);

        // when & then
        assertThatThrownBy(() -> userService.getActiveUser(id));
    }
}