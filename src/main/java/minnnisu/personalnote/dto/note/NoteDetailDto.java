package minnnisu.personalnote.dto.note;

import lombok.*;
import minnnisu.personalnote.domain.Note;
import minnnisu.personalnote.domain.NoteImage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDetailDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private List<String> noteImages;

    public static NoteDetailDto fromEntity(Note note) {
        return NoteDetailDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .updatedAt(note.getUpdatedAt())
                .noteImages(List.of())
                .build();
    }

    public static NoteDetailDto fromEntity(Note note, List<NoteImage> noteImages) {
        return NoteDetailDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .updatedAt(note.getUpdatedAt())
                .noteImages(noteImages.stream().map(NoteImage::getImageName).toList())
                .build();
    }

    public static NoteDetailDto of(
            Long id,
            String title,
            String content,
            LocalDateTime updatedAt
    ) {
        return NoteDetailDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .updatedAt(updatedAt)
                .noteImages(List.of())
                .build();
    }
}
