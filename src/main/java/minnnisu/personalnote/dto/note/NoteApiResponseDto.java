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
public class NoteApiResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoteApiResponseDto fromDto(NoteDto noteDto) {
        return NoteApiResponseDto.builder()
                .id(noteDto.getId())
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .createdAt(noteDto.getCreatedAt())
                .updatedAt(noteDto.getUpdatedAt())
                .build();
    }
}
