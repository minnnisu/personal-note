package minnnisu.personalnote.dto.note;

import lombok.*;
import minnnisu.personalnote.domain.Note;

import java.time.LocalDateTime;

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

    public static NoteDetailDto fromEntity(Note note){
        return NoteDetailDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .updatedAt(note.getUpdatedAt())
                .build();
    }

    public static NoteDetailDto of(
            Long id,
            String title,
            String content,
            LocalDateTime updatedAt
    ){
        return NoteDetailDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .updatedAt(updatedAt)
                .build();
    }
}
