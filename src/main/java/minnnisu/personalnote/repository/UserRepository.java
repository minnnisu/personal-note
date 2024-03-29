package minnnisu.personalnote.repository;

import minnnisu.personalnote.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);
}
