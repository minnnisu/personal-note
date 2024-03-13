package minnnisu.personalnote.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NotValidRequestException(
            HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."
    ),
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
    UserNotFoundException(
            HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."
    ),
    UsernameNotFoundException(
            HttpStatus.NOT_FOUND, "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요."
    ),
    AuthenticationCredentialsNotFoundException(
            HttpStatus.FORBIDDEN, "인증 요청이 거부되었습니다. 관리자에게 문의하세요."
    ),
    UnknownException(
            HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요."
    ),
    NoSuchNoteExistException(
            HttpStatus.NOT_FOUND, "존재하지 않은 노트입니다."
    ),
    NoSuchNoteImageExistError(
            HttpStatus.NOT_FOUND, "존재하지 않은 이미지입니다."
    ),
    NoSuchNoticeExistException(
            HttpStatus.NOT_FOUND, "존재하지 않은 공지사항입니다."
    ),
    QueryParamTypeMismatchError(
            HttpStatus.BAD_REQUEST, "해당 쿼리 파라미터의 타입이 올바르지 않습니다."
    ),
    MissingQueryParamError(
            HttpStatus.BAD_REQUEST, "해당 파라미터의 값이 존재하지 않습니다.."
    ),
    AccessDeniedError(
            HttpStatus.FORBIDDEN, "접근할 수 없는 권한을 가진 사용자입니다."
    ),
    NoImageNameError(
            HttpStatus.BAD_REQUEST, "이미지 이름을 찾을 수 없습니다."
    ),
    NoImageFileError(
            HttpStatus.BAD_REQUEST, "이미지 파일만 업로드할 수 있습니다."
    ),
    SizeLimitExceededError(
            HttpStatus.BAD_REQUEST, "이미지는 최대 10MB까지 업로드할 수 있습니다."
    ),
    InternalServerError(
            HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생하였습니다. 문제가 지속되면 관리자에게 문의하세요."
    );


    private final HttpStatus httpStatus;
    private final String message;

}