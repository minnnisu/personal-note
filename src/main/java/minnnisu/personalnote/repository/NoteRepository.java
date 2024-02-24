package minnnisu.personalnote.repository;

import minnnisu.personalnote.domain.Note;
import minnnisu.personalnote.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserOrderByIdDesc(User user);
    Optional<Note> findByIdAndUser(Long id, User user);
}
