package minnnisu.personalnote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInformation {
    public String username;
    public String name;
    public String email;
}