package minnnisu.personalnote.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.dto.SignUpRequestDto;
import minnnisu.personalnote.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
public class ApiSignUpController {

    private final UserService userService;
    @PostMapping
    public ResponseEntity<?> signup(
            @Valid @ModelAttribute SignUpRequestDto signUpRequestDto
    ) {
        userService.signup(signUpRequestDto);
        return new ResponseEntity<>(Map.of(
                "message", "회원가입에 성공하였습니다"
        ), HttpStatus.CREATED);
    }
}
