package minnnisu.personalnote.dto.note;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteUpdateResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<String> imageNames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoteUpdateResponseDto fromDto(NoteUserDto noteUserDto) {
        return NoteUpdateResponseDto.builder()
                .id(noteUserDto.getId())
                .title(noteUserDto.getTitle())
                .content(noteUserDto.getContent())
                .imageNames(noteUserDto.getImageName())
                .createdAt(noteUserDto.getCreatedAt())
                .updatedAt(noteUserDto.getUpdatedAt())
                .build();
    }
}
