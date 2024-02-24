package minnnisu.personalnote.exception;

import lombok.Getter;
import minnnisu.personalnote.constant.ErrorCode;

@Getter
public class NotValidRequestErrorException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String inputTarget;

    public NotValidRequestErrorException(String inputTarget, ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.inputTarget = inputTarget;
    }
}
