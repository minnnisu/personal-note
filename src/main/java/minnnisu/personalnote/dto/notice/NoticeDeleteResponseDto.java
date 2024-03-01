package minnnisu.personalnote.dto.notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDeleteResponseDto {
    private String message;

    public NoticeDeleteResponseDto() {
        this.message = "성공적으로 공지사항을 삭제하였습니다.";
    }
}
