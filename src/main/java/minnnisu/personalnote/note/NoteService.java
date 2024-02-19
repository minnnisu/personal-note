package minnnisu.personalnote.note;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.user.User;
import minnnisu.personalnote.user.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> findByUser(User user) {
        if(user == null){
            throw new UserNotFoundException();
        }
        if(user.isAdmin()){
            noteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }
        return noteRepository.findByUserOrderByIdDesc(user);
    }

    public Note saveNote(User user, String title, String content) {
       if(user == null) {
           throw new UserNotFoundException();
       }

       return noteRepository.save(new Note(title, content, user));
    }

    public void deleteNote(User user, Long noteId) {
       if(user == null){
           throw new UserNotFoundException();
        }

       Note note = noteRepository.findByIdAndUser(noteId, user);
       if (note != null){
           noteRepository.delete(note);
       }
    }
}
