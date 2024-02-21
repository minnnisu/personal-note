package minnnisu.personalnote.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupErrorResponseDto {
    private Map<String, String> Errors;
}
