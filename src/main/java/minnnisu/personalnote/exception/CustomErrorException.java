package minnnisu.personalnote.exception;

import lombok.Getter;
import minnnisu.personalnote.constant.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class CustomErrorException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomErrorException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
