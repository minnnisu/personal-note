package minnnisu.personalnote.dto;

import lombok.*;
import minnnisu.personalnote.domain.User;

import java.util.Map;

@Getter
@Setter
public class SignupResponseDto {
    private String message;
    private UserInfo userInfo;

    public SignupResponseDto(){}

    public SignupResponseDto(User user){
        this.message = "회원가입에 성공하였습니다.";
        this.userInfo = new UserInfo(user.getUsername(), user.getName(), user.getEmail());
    }
}

@Getter
@AllArgsConstructor
class UserInfo {
    private final String username;
    private final String name;
    private final String email;
}
