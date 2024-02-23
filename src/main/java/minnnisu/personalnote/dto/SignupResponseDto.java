package minnnisu.personalnote.dto;

import lombok.Getter;
import lombok.Setter;
import minnnisu.personalnote.domain.User;

@Getter
@Setter
public class SignupResponseDto {
    private String message;
    private UserInformation userInformation;

    public SignupResponseDto(User user){
        this.message = "회원가입에 성공하였습니다.";
        this.userInformation = new UserInformation(user.getUsername(), user.getName(), user.getEmail());
    }
}
