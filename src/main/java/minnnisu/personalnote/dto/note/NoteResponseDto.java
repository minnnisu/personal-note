package minnnisu.personalnote.dto.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NoteResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoteResponseDto fromDto(NoteDto noteDto) {
        return NoteResponseDto.builder()
                .id(noteDto.getId())
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .createdAt(noteDto.getCreatedAt())
                .updatedAt(noteDto.getUpdatedAt())
                .build();
    }
}
