package minnnisu.personalnote.repository;

import minnnisu.personalnote.domain.Note;
import minnnisu.personalnote.domain.NoteImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteImageRepository extends JpaRepository<NoteImage, Long> {
    List<NoteImage> findNoteImageByNote(Note note);
}
