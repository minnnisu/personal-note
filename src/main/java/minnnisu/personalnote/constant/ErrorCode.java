package minnnisu.personalnote.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DuplicatedUserName(
            HttpStatus.CONFLICT, "중복된 아이디입니다."
    ),
    NotMatchedPassword(
            HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다."
    ),
    BadCredentialsException(
            HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요."
    ),
    InternalAuthenticationServiceException(
            HttpStatus.INTERNAL_SERVER_ERROR, "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요."
    ),
    UsernameNotFoundException(
            HttpStatus.NOT_FOUND, "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요."
    ),
    AuthenticationCredentialsNotFoundException(
            HttpStatus.FORBIDDEN, "인증 요청이 거부되었습니다. 관리자에게 문의하세요."
    ),
    UnknownException(
            HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요."
    );

    private final HttpStatus httpStatus;
    private final String message;

}