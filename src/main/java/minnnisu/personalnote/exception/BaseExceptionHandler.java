package minnnisu.personalnote.exception;

import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {
    // TODO
    @ExceptionHandler(CustomErrorException.class)
    protected String handleCustomErrorException(CustomErrorException e, Model model) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.fromException(e);
        model.addAttribute("message", errorResponseDto.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected String HandleGeneralException(Exception e, HttpServletRequest request, Model model){
        log.info("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
