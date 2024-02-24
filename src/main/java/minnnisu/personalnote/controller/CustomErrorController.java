package minnnisu.personalnote.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    /**
     * View 에러처리 컨트롤러
     */

    @RequestMapping(path = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());

        return new ModelAndView(
                "error",
                Map.of(
                        "message", status.getReasonPhrase()
                ),
                status
        );
    }

    /**
     *   API 에러처리 컨트롤러
     */

//    @RequestMapping("/error")
//    public ResponseEntity<APIErrorResponse> error(HttpServletResponse response) {
//        HttpStatus status = HttpStatus.valueOf(response.getStatus());
//        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;
//
//        return ResponseEntity
//                .status(status)
//                .body(APIErrorResponse.of(false, errorCode));
//    }

}