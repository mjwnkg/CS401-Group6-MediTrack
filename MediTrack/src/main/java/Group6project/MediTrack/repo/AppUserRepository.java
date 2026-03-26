package Group6project.MediTrack.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Group6project.MediTrack.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
