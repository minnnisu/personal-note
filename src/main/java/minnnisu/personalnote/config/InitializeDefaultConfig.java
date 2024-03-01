package minnnisu.personalnote.config;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.dto.notice.NoticeRequestDto;
import minnnisu.personalnote.dto.signup.SignUpRequestDto;
import minnnisu.personalnote.dto.signup.SignupResponseDto;
import minnnisu.personalnote.service.NoteService;
import minnnisu.personalnote.service.NoticeService;
import minnnisu.personalnote.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 초기 상태 등록 Config
 */
@Configuration
@RequiredArgsConstructor
@Profile(value = "!test") // test 에서는 제외
public class InitializeDefaultConfig {

    private final UserService userService;
    private final NoteService noteService;
    private final NoticeService noticeService;

    /**
     * 유저등록, note 4개 등록
     */
    @Bean
    public void initializeDefaultUser() {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto
                .builder()
                .username("user1")
                .password("user12345#")
                .repeatedPassword("user12345#")
                .name("minnnisu")
                .email("mine1234@naver.com")
                .build();
        SignupResponseDto signupResponseDto = userService.signup(signUpRequestDto);
//        noteService.saveNote(user, "테스트", "테스트입니다.");
//        noteService.saveNote(user, "테스트2", "테스트2입니다.");
//        noteService.saveNote(user, "테스트3", "테스트3입니다.");
//        noteService.saveNote(user, "여름 여행계획", "여름 여행계획 작성중...");
    }

    /**
     * 어드민등록, 공지사항 2개 등록
     */
    @Bean
    public void initializeDefaultAdmin() {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto
                .builder()
                .username("admin")
                .password("admin12345#")
                .repeatedPassword("admin12345#")
                .name("minnnisu")
                .email("mine1234@naver.com")
                .build();
        userService.signupAdmin(signUpRequestDto);

        noticeService.saveNotice(NoticeRequestDto.of("환영합니다.", "환영합니다 여러분"));
        noticeService.saveNotice(NoticeRequestDto.of("노트 작성 방법 공지", "1. 회원가입\n2. 로그인\n3. 노트 작성\n4. 저장\n* 본인 외에는 게시글을 볼 수 없습니다."));

    }
}
