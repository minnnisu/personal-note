package minnnisu.personalnote.service;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.signup.SignUpRequestDto;
import minnnisu.personalnote.dto.signup.SignupResponseDto;
import minnnisu.personalnote.exception.NotValidRequestErrorException;
import minnnisu.personalnote.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto signup(
            SignUpRequestDto signUpRequestDto
    ) {
        validateSignup(signUpRequestDto);
        User user = new User(
                signUpRequestDto.getUsername(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                signUpRequestDto.getName(),
                signUpRequestDto.getEmail(),
                "ROLE_USER"

        );

        User newUser = userRepository.save(user);
        return new SignupResponseDto(newUser);
    }



    public User signupAdmin(
            SignUpRequestDto signUpRequestDto
    ) {
        validateSignup(signUpRequestDto);
        User user = new User(
                signUpRequestDto.getUsername(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                signUpRequestDto.getName(),
                signUpRequestDto.getEmail(),
                "ROLE_ADMIN"

        );
        return userRepository.save(user);
    }

    public void validateSignup(SignUpRequestDto signUpRequestDto) {
        if (userRepository.findByUsername(signUpRequestDto.getUsername()) != null) {
            throw new NotValidRequestErrorException("username", ErrorCode.DuplicatedUserName);
        }

        if(!signUpRequestDto.getPassword().equals(signUpRequestDto.getRepeatedPassword())) {
            throw new NotValidRequestErrorException("repeatedPassword", ErrorCode.NotMatchedPassword);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}