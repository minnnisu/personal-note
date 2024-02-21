package minnnisu.personalnote.exception;

import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(CustomErrorException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomErrorException(CustomErrorException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
        return new ResponseEntity<>(errorResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<?> handleBindException(org.springframework.validation.BindException e) {
        List<FieldError> list = e.getBindingResult().getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : list){
            errorMap.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
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
