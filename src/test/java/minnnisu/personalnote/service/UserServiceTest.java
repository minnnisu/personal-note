package minnnisu.personalnote.service;

import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.SignUpRequestDto;
import minnnisu.personalnote.dto.SignupResponseDto;
import minnnisu.personalnote.exception.SignupErrorException;
import minnnisu.personalnote.repository.UserRepository;
import minnnisu.personalnote.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService sut;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void givenUserInfo_whenRequestingSignup_thenReturnSuccessResponse() {
        User user = new User(
                "user12",
                "password12#",
                "민수",
                "mine1111@naver.com",
                "ROLE_USER");

        // Given
        given(userRepository.findByUsername(user.getUsername())).willReturn(null);
        given(passwordEncoder.encode(any())).willReturn("{bcrypt}$2a$10$XbhxXTmYU5.tn4g26vDG8ePoaDMM3EHLsAwL0NlHJNzzklwFx9uM2");

        SignUpRequestDto signUpRequestDto = SignUpRequestDto
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .repeatedPassword(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        User newUser = new User(
                1L,
                signUpRequestDto.getUsername(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                "ROLE_USER",
                signUpRequestDto.getName(),
                signUpRequestDto.getEmail()
        );
        given(userRepository.save(any())).willReturn(newUser);

        // When
        SignupResponseDto signupResponseDto = sut.signup(signUpRequestDto);

        // Then
        Assertions.assertThat(signupResponseDto.getMessage())
                .isEqualTo("회원가입에 성공하였습니다.");
        Assertions.assertThat(signupResponseDto.getUserInformation().getUsername())
                .isEqualTo(user.getUsername());
        Assertions.assertThat(signupResponseDto.getUserInformation().getName())
                .isEqualTo(user.getName());
        Assertions.assertThat(signupResponseDto.getUserInformation().getEmail())
                .isEqualTo(user.getEmail());
    }

    @Test
    void givenDuplicatedUserId_whenRequestingSignup_thenThrowsSignupErrorException() {
        User notValidUser = new User(
                "user1",
                "password12#",
                "민수",
                "mine1111@naver.com",
                "ROLE_USER");

        User storedUser = new User(
                "user1",
                "{bcrypt}$2a$10$XbhxXTmYU5.tn4g26vDG8ePoaDMM3EHLsAwL0NlHJNzzklwFx9uM2",
                "minnisu",
                "mine1111@naver.com",
                "ROLE_USER"
        );


        // Given
        given(userRepository.findByUsername(notValidUser.getUsername())).willReturn(storedUser);

        SignUpRequestDto signUpRequestDto = SignUpRequestDto
                .builder()
                .username(notValidUser.getUsername())
                .password(notValidUser.getPassword())
                .repeatedPassword(notValidUser.getPassword())
                .name(notValidUser.getName())
                .email(notValidUser.getEmail())
                .build();

        // When
        Throwable thrown = catchThrowable(() -> sut.signup(signUpRequestDto));
        Assertions.assertThat(thrown)
                .isInstanceOf(SignupErrorException.class)
                .hasMessage(ErrorCode.DuplicatedUserName.getMessage());
    }

    @Test
    void givenNotMatchedPassword_whenRequestingSignup_thenThrowsSignupErrorException() {
        User user = new User(
                "user1",
                "password12#",
                "민수",
                "mine1111@naver.com",
                "ROLE_USER");


        SignUpRequestDto signUpRequestDto = SignUpRequestDto
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .repeatedPassword("password12")
                .name(user.getName())
                .email(user.getEmail())
                .build();

        // When
        Throwable thrown = catchThrowable(() -> sut.signup(signUpRequestDto));
        Assertions.assertThat(thrown)
                .isInstanceOf(SignupErrorException.class)
                .hasMessage(ErrorCode.NotMatchedPassword.getMessage());
    }

}