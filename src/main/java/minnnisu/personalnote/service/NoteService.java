package minnnisu.personalnote.service;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.Note;
import minnnisu.personalnote.dto.note.NoteDto;
import minnnisu.personalnote.dto.note.NoteRequestDto;
import minnnisu.personalnote.exception.CustomErrorException;
import minnnisu.personalnote.repository.NoteRepository;
import minnnisu.personalnote.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<NoteDto> findByUser(User user) {
        validateUserExist(user);
        List<Note> notes = noteRepository.findByUserOrderByIdDesc(user);
        return notes
                .stream()
                .map(NoteDto::fromEntity)
                .toList();

    }

    public NoteDto saveNote(User user, NoteRequestDto noteRequestDto) {
        validateUserExist(user);
        Note note = noteRepository.save(new Note(
                noteRequestDto.getTitle(),
                noteRequestDto.getContent(),
                user
        ));

        return NoteDto.fromEntity(note);
    }

    public void deleteNote(User user, Long noteId) {
        validateUserExist(user);
        Note note = noteRepository.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchNoteExistException));
        noteRepository.delete(note);
    }

    void validateUserExist(User user){
        if(user == null){
            throw new CustomErrorException(ErrorCode.UserNotFoundException);
        }
    }
}
