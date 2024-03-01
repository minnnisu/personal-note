package minnnisu.personalnote.dto.note;

import lombok.*;
import minnnisu.personalnote.domain.Note;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteAdminDto {
    private String title;
    private User user;
    private LocalDateTime createdAt;

    public static NoteAdminDto fromEntity(Note note) {
        return NoteAdminDto.builder()
                .title(note.getTitle())
                .user(User.of(note.getUser().getUsername()))
                .createdAt(note.getCreatedAt())
                .build();
    }


    @Getter
    @AllArgsConstructor
    @Builder
    public static class User {
        private String username;

        public static User of(String username) {
            return User.builder().username(username).build();
        }
    }
}
