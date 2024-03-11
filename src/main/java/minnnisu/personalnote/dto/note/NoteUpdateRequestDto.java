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
