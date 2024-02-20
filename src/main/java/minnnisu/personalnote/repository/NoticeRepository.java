package minnnisu.personalnote.repository;

import minnnisu.personalnote.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
