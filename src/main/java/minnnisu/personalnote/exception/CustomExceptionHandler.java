package minnnisu.personalnote.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String HandleGeneralException(Exception e, HttpServletRequest request, Model model){
        log.info("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
