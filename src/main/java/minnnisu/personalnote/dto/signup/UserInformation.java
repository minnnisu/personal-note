package minnnisu.personalnote.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInformation {
    public String username;
    public String name;
    public String email;
}