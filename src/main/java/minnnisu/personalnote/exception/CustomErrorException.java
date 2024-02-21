package minnnisu.personalnote.exception;

import lombok.Getter;
import minnnisu.personalnote.constant.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class CustomErrorException extends RuntimeException{
    private final HttpStatus httpStatus;

    public CustomErrorException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }
}
