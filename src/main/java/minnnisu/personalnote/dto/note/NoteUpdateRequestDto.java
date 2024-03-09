package minnnisu.personalnote.dto.note;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteUpdateRequestDto {
    private String title;
    private String content;
    private List<String> noteImages;

    public static NoteUpdateRequestDto of(
            String title,
            String content,
            List<String> noteImages
    ) {
        return NoteUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .noteImages(noteImages)
                .build();
    }
}
