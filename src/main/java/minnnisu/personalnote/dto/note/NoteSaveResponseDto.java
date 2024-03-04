package minnnisu.personalnote.dto.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NoteSaveResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<String> imageName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoteSaveResponseDto fromDto(NoteUserDto noteUserDto) {
        return NoteSaveResponseDto.builder()
                .id(noteUserDto.getId())
                .title(noteUserDto.getTitle())
                .content(noteUserDto.getContent())
                .imageName(noteUserDto.getImageName())
                .createdAt(noteUserDto.getCreatedAt())
                .updatedAt(noteUserDto.getUpdatedAt())
                .build();
    }
}
