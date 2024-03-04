package minnnisu.personalnote.dto.note;

import lombok.*;
import minnnisu.personalnote.domain.NoteImage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteImageDto {
    private Long id;
    private NoteUserDto noteUserDto;
    private String imageName;

    public static NoteImageDto fromEntity(NoteImage noteImage) {
        return NoteImageDto.builder()
                .id(noteImage.getId())
                .noteUserDto(NoteUserDto.fromEntity(noteImage.getNote()))
                .imageName(noteImage.getImageName())
                .build();
    }

    public static NoteImageDto of(
            Long id,
            NoteUserDto noteUserDto,
            String imageName
    ) {
        return NoteImageDto.builder()
                .id(id)
                .noteUserDto(noteUserDto)
                .imageName(imageName)
                .build();
    }

}
