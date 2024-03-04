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
@EqualsAndHashCode
public class NoteUserDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> imageName;

    public static NoteUserDto fromEntity(Note note) {
        return NoteUserDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .imageName(List.of())
                .build();
    }

    public static NoteUserDto fromEntity(Note note, List<NoteImage> noteImages) {
        return NoteUserDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .imageName(noteImages.stream().map(NoteImage::getImageName).toList())
                .build();
    }


    public static NoteUserDto of(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return NoteUserDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .imageName(List.of())
                .build();
    }

}
