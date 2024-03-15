package minnnisu.personalnote.dto.note;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteImageListResponseDto {
    private List<String> imageNames;

    public static NoteImageListResponseDto fromDto(List<NoteImageDto> noteImageDtos) {
        return NoteImageListResponseDto.builder()
                .imageNames(noteImageDtos.stream().map(NoteImageDto::getImageName).toList())
                .build();
    }

    public static NoteImageListResponseDto of(
            List<String> imageNames
    ) {
        return NoteImageListResponseDto.builder()
                .imageNames(imageNames)
                .build();
    }

}