package minnnisu.personalnote.dto;

import lombok.*;
import minnnisu.personalnote.exception.CustomErrorException;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
    private String errorCode;
    private String message;

    public static ErrorResponseDto fromException(CustomErrorException e){
        return ErrorResponseDto.builder()
                .errorCode(e.getErrorCode().name())
                .message(e.getMessage())
                .build();
    }

    public static ErrorResponseDto of(String errorCode, String message){
        return ErrorResponseDto.builder()
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}
