package minnnisu.personalnote.dto.note;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteSaveRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 20, message = "최대 20자까지 입력 가능합니다.")
    private String title;
    @NotBlank(message = "본문을 작성해세요.")
    private String content;
}
