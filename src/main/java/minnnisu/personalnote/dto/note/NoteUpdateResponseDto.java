package minnnisu.personalnote.dto.note;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteUpdateResponseDto {
    private String title;
    private String content;
    private List<String> deletedImages;
    private List<String> createdImages;
}
