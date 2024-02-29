package minnnisu.personalnote.exception;

import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {
    // TODO
    @ExceptionHandler(CustomErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleCustomErrorException(CustomErrorException e, Model model) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.fromException(e);
        model.addAttribute("message", errorResponseDto.getMessage());
        return "error";
    }

    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            NotValidRequestErrorException.class,
            BindException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleNotValidRequestErrorException(MethodArgumentTypeMismatchException e, Model model) {
        model.addAttribute("message", ErrorCode.NotValidRequestException.getMessage());
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected String handleAccessDeniedException(AccessDeniedException e, Model model) {
        model.addAttribute("message", ErrorCode.AccessDeniedError.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected String HandleGeneralException(Exception e, HttpServletRequest request, Model model) {
        log.info("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
