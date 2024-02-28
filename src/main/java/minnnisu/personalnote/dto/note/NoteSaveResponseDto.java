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
public class NoteSaveResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoteSaveResponseDto fromDto(NoteDto noteDto) {
        return NoteSaveResponseDto.builder()
                .id(noteDto.getId())
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .createdAt(noteDto.getCreatedAt())
                .updatedAt(noteDto.getUpdatedAt())
                .build();
    }
}
