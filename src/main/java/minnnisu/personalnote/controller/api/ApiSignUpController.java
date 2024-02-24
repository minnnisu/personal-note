package minnnisu.personalnote.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.dto.signup.SignUpRequestDto;
import minnnisu.personalnote.dto.signup.SignupResponseDto;
import minnnisu.personalnote.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
public class ApiSignUpController {

    private final UserService userService;
    @PostMapping
    public ResponseEntity<?> signup(
            @Valid @RequestBody SignUpRequestDto signUpRequestDto
    ) {
        SignupResponseDto signupResponseDto = userService.signup(signUpRequestDto);
        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }
}
