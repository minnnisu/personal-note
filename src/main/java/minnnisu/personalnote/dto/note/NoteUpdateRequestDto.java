package minnnisu.personalnote.dto.note;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteUpdateRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 20, message = "최대 20자까지 입력 가능합니다.")
    private String title;
    @NotBlank(message = "본문을 작성해세요.")
    private String content;
    private List<String> deletionTargetImages;

    public static NoteUpdateRequestDto of(
            String title,
            String content,
            List<String> deletionTargetImages
    ) {
        return NoteUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .deletionTargetImages(deletionTargetImages)
                .build();
    }
}
