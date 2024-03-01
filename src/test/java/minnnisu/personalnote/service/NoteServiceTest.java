package minnnisu.personalnote.service;

import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.Note;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.note.NoteRequestDto;
import minnnisu.personalnote.dto.note.NoteUserDto;
import minnnisu.personalnote.exception.CustomErrorException;
import minnnisu.personalnote.repository.NoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @InjectMocks
    NoteService sut;

    @Mock
    NoteRepository noteRepository;

    @Test
    void givenNothing_WhenRequestingNoteListReturnNoteList() {
        // Given
        User user = createUser();
        Note note = createNote(user, "새로운 노트", "새로운 노트입니다.");
        given(noteRepository.findByUserOrderByIdDesc(user))
                .willReturn(List.of(note));

        // When
        List<NoteUserDto> result = sut.findByUser(user);

        // Then
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getId()).isEqualTo(NoteUserDto.fromEntity(note).getId());
        Assertions.assertThat(result.get(0).getTitle()).isEqualTo(NoteUserDto.fromEntity(note).getTitle());
        Assertions.assertThat(result.get(0).getContent()).isEqualTo(NoteUserDto.fromEntity(note).getContent());
        Assertions.assertThat(result.get(0).getCreatedAt()).isEqualTo(NoteUserDto.fromEntity(note).getCreatedAt());
        Assertions.assertThat(result.get(0).getUpdatedAt()).isEqualTo(NoteUserDto.fromEntity(note).getUpdatedAt());
    }

    @Test
    void givenNewNote_whenSavingNewNote_thenSaveNewNoteAndReturnNoteDto() {
        // Given
        User user = createUser();
        Note note = createNote(user, "새로운 노트", "새로운 노트입니다.");
        NoteRequestDto noteRequestDto =
                NoteRequestDto.builder()
                        .title("새로운 노트")
                        .content("새로운 노트입니다.")
                        .build();

        given(noteRepository.save(any())).willReturn(note);


        // When
        NoteUserDto result = sut.saveNote(user, noteRequestDto);

        // Then
        Assertions.assertThat(result.getId()).isEqualTo(note.getId());
        Assertions.assertThat(result.getTitle()).isEqualTo(note.getTitle());
        Assertions.assertThat(result.getContent()).isEqualTo(note.getContent());
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(note.getCreatedAt());
        Assertions.assertThat(result.getUpdatedAt()).isEqualTo(note.getUpdatedAt());
    }

    @Test
    void givenNoExistNoteId_whenDeletingNote_thenThrowsException() {
        // Given
        User user = createUser();

        // When
        given(noteRepository.findByIdAndUser(2L, user))
                .willThrow(new CustomErrorException(ErrorCode.NoSuchNoteExistException));
        Throwable thrown = catchThrowable(() -> sut.deleteNote(user, 2L));

        // Then
        Assertions.assertThat(thrown)
                .isInstanceOf(CustomErrorException.class)
                .hasMessage(ErrorCode.NoSuchNoteExistException.getMessage());
    }

    private User createUser() {
        return User.of(
                1L,
                "minnnisu",
                "password12#",
                "ROLE_USER",
                "minnnisu",
                "minnnisu@minnnisu.com"
        );
    }

    private Note createNote(User user, String title, String content) {
        return Note.of(
                1L,
                title,
                content,
                user,
                LocalDateTime.of(2023, 1, 1, 14, 0, 0),
                LocalDateTime.of(2023, 1, 1, 16, 0, 0)
        );

    }

}