package minnnisu.personalnote.repository;

import minnnisu.personalnote.domain.NoteImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteImageRepository extends JpaRepository<NoteImage, Long> {
}
