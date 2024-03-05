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
    private String content;
    private LocalDateTime createdAt;

    public static NoteSummaryDto fromEntity(Note note) {
        return NoteSummaryDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(resizeContent(note.getContent()))
                .createdAt(note.getCreatedAt())
                .build();
    }

    public static NoteSummaryDto of(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt
    ) {
        return NoteSummaryDto.builder()
                .id(id)
                .title(title)
                .content(resizeContent(content))
                .createdAt(createdAt)
                .build();
    }


    static String resizeContent(String content) {
        if (content.length() > 15) {
            content = content.substring(0, 15) + "...";
        }

        return content;
    }
}
