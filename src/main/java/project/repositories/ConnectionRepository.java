package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    
}
