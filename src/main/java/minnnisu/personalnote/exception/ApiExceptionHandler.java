package minnnisu.personalnote.exception;

import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.dto.ErrorResponseDto;
import minnnisu.personalnote.dto.NotValidRequestErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(CustomErrorException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomErrorException(CustomErrorException e) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.fromException(e);
        return new ResponseEntity<>(errorResponseDto, e.getErrorCode().getHttpStatus());
    }

    // TODO
    @ExceptionHandler(NotValidRequestErrorException.class)
    protected ResponseEntity<NotValidRequestErrorResponseDto> handleNotValidRequestErrorException(NotValidRequestErrorException e) {
        NotValidRequestErrorResponseDto.ErrorDescription errorDescription
                = NotValidRequestErrorResponseDto.ErrorDescription.of(e.getInputTarget(), e.getMessage());
        List<NotValidRequestErrorResponseDto.ErrorDescription> ErrorDescriptions
                = List.of(errorDescription);

        NotValidRequestErrorResponseDto notValidRequestErrorResponseDto
                = NotValidRequestErrorResponseDto.of(ErrorDescriptions);

        return new ResponseEntity<>(notValidRequestErrorResponseDto, e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<NotValidRequestErrorResponseDto> handleBindException(org.springframework.validation.BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<NotValidRequestErrorResponseDto.ErrorDescription> errorDescriptions =
                fieldErrors.stream().map(NotValidRequestErrorResponseDto.ErrorDescription::of).toList();

        NotValidRequestErrorResponseDto notValidRequestErrorResponseDto =
                NotValidRequestErrorResponseDto.of(e, errorDescriptions);

        return new ResponseEntity<>(notValidRequestErrorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> HandleGeneralException(Exception e){
        ErrorResponseDto errorResponseDto =
                ErrorResponseDto.of(
                        ErrorCode.InternalServerError.name(),
                        ErrorCode.InternalServerError.getMessage()
                );

        return new ResponseEntity<>(errorResponseDto, ErrorCode.InternalServerError.getHttpStatus());
    }
}
