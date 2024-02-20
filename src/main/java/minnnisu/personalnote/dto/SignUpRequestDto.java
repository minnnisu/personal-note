package minnnisu.personalnote.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SignUpRequestDto {
    private String username;
    private String password;
}
