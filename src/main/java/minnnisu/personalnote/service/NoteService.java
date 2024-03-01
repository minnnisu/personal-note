package minnnisu.personalnote.service;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.Note;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.dto.note.NoteAdminDto;
import minnnisu.personalnote.dto.note.NoteRequestDto;
import minnnisu.personalnote.dto.note.NoteUserDto;
import minnnisu.personalnote.exception.CustomErrorException;
import minnnisu.personalnote.repository.NoteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<NoteAdminDto> findAll() {
        List<Note> notes = noteRepository.findAll();
        return notes
                .stream()
                .map(NoteAdminDto::fromEntity)
                .toList();
    }

    public List<NoteUserDto> findByUser(User user) {
        List<Note> notes = noteRepository.findByUserOrderByIdDesc(user);

        return notes
                .stream()
                .map(NoteUserDto::fromEntity)
                .toList();
    }

    public NoteUserDto saveNote(User user, NoteRequestDto noteRequestDto) {
        Note note = noteRepository.save(new Note(
                noteRequestDto.getTitle(),
                noteRequestDto.getContent(),
                user
        ));

        return NoteUserDto.fromEntity(note);
    }

    public void deleteNote(User user, Long noteId) {
        Note note = noteRepository.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchNoteExistException));
        noteRepository.delete(note);
    }
}
