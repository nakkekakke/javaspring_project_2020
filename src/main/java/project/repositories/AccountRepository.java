package project.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    
    Account findByProfileId(String profileId);

    Account findByName(String name);

    List<Account> findByNameIgnoreCaseContaining(String name);
}
