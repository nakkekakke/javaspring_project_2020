package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
