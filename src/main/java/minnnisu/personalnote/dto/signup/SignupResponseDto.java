package minnnisu.personalnote.dto.signup;

import lombok.*;
import minnnisu.personalnote.domain.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponseDto {
    private String message;
    private UserInformation userInformation;

    public SignupResponseDto(User user){
        this.message = "회원가입에 성공하였습니다.";
        this.userInformation = UserInformation.fromEntity(user);
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserInformation {
        public String username;
        public String name;
        public String email;

        public static UserInformation fromEntity(User user) {
            return UserInformation.builder()
                    .username(user.getUsername())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }
    }
}
