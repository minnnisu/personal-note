package minnnisu.personalnote.exception;

import lombok.Getter;
import minnnisu.personalnote.constant.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class SignupErrorException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String inputTarget;

    public SignupErrorException( String inputTarget, ErrorCode errorCode){
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
        this.inputTarget = inputTarget;
    }
}
