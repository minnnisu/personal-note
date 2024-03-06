package minnnisu.personalnote.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @Lob
    private String content;

    /**
     * @CreatedDate 어노테이션으로 인해 Entity가 생성되어 저장될 때 시간이 자동으로 저장되고,
     * 마찬가지 @LastModifiedDate는 조회한 Entity의 값을 변경할 때 시간이 자동으로 저장되게 됩니다.
     * @EnableJpaAuditing 활성화 필요
     */

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Notice(
            String title,
            String content
    ) {
        this.title = title;
        this.content = content;
    }

    public static Notice of(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return Notice.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
