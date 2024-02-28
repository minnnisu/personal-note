package minnnisu.personalnote.dto.note;

import lombok.*;

@Getter
@Setter
public class NoteDeleteResponseDto {
    public String message;

    public NoteDeleteResponseDto() {
        this.message = "성공적으로 노트를 삭제하였습니다.";
    }
}
