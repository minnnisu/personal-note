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

    public static NoteSaveResponseDto fromDto(NoteUserDto noteUserDto) {
        return NoteSaveResponseDto.builder()
                .id(noteUserDto.getId())
                .title(noteUserDto.getTitle())
                .content(noteUserDto.getContent())
                .createdAt(noteUserDto.getCreatedAt())
                .updatedAt(noteUserDto.getUpdatedAt())
                .build();
    }
}
