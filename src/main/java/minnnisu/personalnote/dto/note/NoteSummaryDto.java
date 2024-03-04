package minnnisu.personalnote.dto.note;


import lombok.*;
import minnnisu.personalnote.domain.Note;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteSummaryDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public static NoteSummaryDto fromEntity(Note note) {
        return NoteSummaryDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .createdAt(note.getCreatedAt())
                .build();
    }

    public static NoteSummaryDto of(
            Long id,
            String title,
            LocalDateTime createdAt
    ) {
        return NoteSummaryDto.builder()
                .id(id)
                .title(title)
                .createdAt(createdAt)
                .build();
    }
}
