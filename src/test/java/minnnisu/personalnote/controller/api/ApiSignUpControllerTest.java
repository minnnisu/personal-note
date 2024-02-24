package minnnisu.personalnote.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.signup.SignUpRequestDto;
import minnnisu.personalnote.dto.NotValidRequestErrorResponseDto;
import minnnisu.personalnote.dto.signup.SignupResponseDto;
import minnnisu.personalnote.exception.NotValidRequestErrorException;
import minnnisu.personalnote.service.UserService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ApiSignUpController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
)
class ApiSignUpControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    UserService userService;

    public ApiSignUpControllerTest(@Autowired MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }


    @Test
    void givenUserInfo_whenRequestingSignup_thenReturnSuccessResponse() throws Exception {
        User user = new User(
                "user12",
                "password12#",
                "민수",
                "mine1111@naver.com",
                "ROLE_USER");

        // Given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .repeatedPassword(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(signUpRequestDto);

        SignupResponseDto signupResponseDto = new SignupResponseDto(user);
        given(userService.signup(any())).willReturn(signupResponseDto);

        // When & Then
        String responseBody = new ObjectMapper().writeValueAsString(signupResponseDto);

        mockMvc.perform(
                        post("/api/signup").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    void givenNotValidUserInfo_whenRequestingSignup_thenReturnErrorResponse() throws Exception {
        User user = new User(
                "user12",
                "ps",
                "민수",
                "mine1111naver.com",
                "ROLE_USER");

        // Given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto
                .builder()
                .username(user.getUsername())
                .password(null)
                .repeatedPassword(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(signUpRequestDto);

        // When & Then
        Map<String, String> responseMap = Map.of(
            "password", "비밀번호를 입력해주세요",
            "repeatedPassword", "비밀번호는 8자 이상 20자 이하로 입력해주세요.",
            "email", "올바른 이메일 주소를 입력해주세요."
        );

        List<NotValidRequestErrorResponseDto.ErrorDescription> errorDescriptions = List.of(
                new NotValidRequestErrorResponseDto.ErrorDescription("password", "비밀번호를 입력해주세요"),
                new NotValidRequestErrorResponseDto.ErrorDescription("repeatedPassword", "비밀번호는 8자 이상 20자 이하로 입력해주세요."),
                new NotValidRequestErrorResponseDto.ErrorDescription("email", "올바른 이메일 주소를 입력해주세요.")
        );
        NotValidRequestErrorResponseDto notValidRequestErrorResponseDto =
                new NotValidRequestErrorResponseDto(
                        ErrorCode.NotValidRequestException.name(),
                        ErrorCode.NotValidRequestException.getMessage(),
                        errorDescriptions
                );
        String responseBody = new ObjectMapper().writeValueAsString(notValidRequestErrorResponseDto);

        mockMvc.perform(
                        post("/api/signup").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(responseBody));
    }

    @Test
    void givenNotValidUserInfo_whenRequestingSignup_thenReturnErrorResponse2() throws Exception {
        User user = new User(
                "user1",
                "password12#",
                "민수",
                "mine1111@naver.com",
                "ROLE_USER");

        // Given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .repeatedPassword(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        when(userService.signup(any())).thenThrow(new NotValidRequestErrorException("username", ErrorCode.DuplicatedUserName));


        // When & Then
        List<NotValidRequestErrorResponseDto.ErrorDescription> errorDescriptions = List.of(
                new NotValidRequestErrorResponseDto.ErrorDescription("username", "중복된 아이디입니다.")
        );
        NotValidRequestErrorResponseDto notValidRequestErrorResponseDto =
                new NotValidRequestErrorResponseDto(
                        ErrorCode.NotValidRequestException.name(),
                        ErrorCode.NotValidRequestException.getMessage(),
                        errorDescriptions
                );
        String responseBody = new ObjectMapper().writeValueAsString(notValidRequestErrorResponseDto);

        mockMvc.perform(
                        post("/api/signup").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(signUpRequestDto))
                )
                .andExpect(status().isConflict())
                .andExpect(content().json(responseBody));
    }

}