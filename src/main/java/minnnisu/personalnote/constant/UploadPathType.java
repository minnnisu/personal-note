package minnnisu.personalnote.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadPathType {
    IMAGE("src/main/resources/static/images/note");

    private final String Path;
}
