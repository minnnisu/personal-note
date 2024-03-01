package minnnisu.personalnote.dto.notice;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeSaveResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoticeSaveResponseDto fromNoticeDto(NoticeDto noticeDto) {
        return NoticeSaveResponseDto.builder()
                .id(noticeDto.getId())
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .createdAt(noticeDto.getCreatedAt())
                .updatedAt(noticeDto.getUpdatedAt())
                .build();
    }
}
