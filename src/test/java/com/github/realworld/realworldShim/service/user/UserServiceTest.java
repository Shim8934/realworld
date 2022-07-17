package com.github.realworld.realworldShim.service.user;

import com.github.realworld.realworldShim.model.user.Email;
import com.github.realworld.realworldShim.model.user.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    private final Long userId = 4L;
    private Email email;
    private String username;
    private String password;
    private String bio;

    @BeforeEach
    void setUp() {
        email = new Email("shim01@gmail.com");
        username = "심기방기";
        password = "1234";
        bio = "테스트 유저 심기방기 자기소개 입니다.";
    }

    @Test
    @Order(1)
    void 유저를_추가한다() {
        User user = userService.join(email, username, password);
        assertThat(user, is(notNullValue()));
        assertThat(user.getSeq(), is(notNullValue()));
        assertThat(user.getEmail(), is(notNullValue()));
        assertThat(user.getProfile(), is(notNullValue()));
        log.info("Inserted User : {}", user);
    }

    @Test
    @Order(2)
    void 유저를_ID로_조회한다() {
        User findByIdUser = userService.findById(userId).orElse(null);
        assertThat(findByIdUser, is(notNullValue()));
        assertThat(findByIdUser.getEmail(), is(email));
        log.info("Found by Id {}: {}", email, findByIdUser);
    }

    @Test
    @Order(3)
    void 유저를_Email로_조회한다() {
        User findByEmailUser = userService.findByEmail(email).orElse(null);
        assertThat(findByEmailUser, is(notNullValue()));
        assertThat(findByEmailUser.getEmail(), is(email));
        log.info("Found by Email {}: {}", email, findByEmailUser);
    }
}