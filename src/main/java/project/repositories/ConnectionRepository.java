package project.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.domain.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    
    Connection findByRequesterProfileIdAndTargetProfileId(String requesterProfileId, String targetProfileId);
    
    List<Connection> findByTargetProfileIdAndConfirmed(String targetProfileId, boolean confirmed);
    
    @Query("select c from ACCOUNTCONNECTION c where (c.confirmed = ?3 and c.requesterProfileId = ?1) or (c.confirmed = ?3 and c.targetProfileId = ?2)")
    List<Connection> findByConfirmedAndRequesterProfileIdOrConfirmedAndTargetProfileId(String requesterProfileId, String targetProfileId, boolean confirmed);
    
    @Query("delete from ACCOUNTCONNECTION c where c.id = ?1")
    @Override
    void deleteById(Long id);

    @Query("select c from ACCOUNTCONNECTION c where (c.requesterProfileId = ?1 AND c.targetProfileId = ?2) OR (c.requesterProfileId = ?2 AND c.targetProfileId = ?1)")
    public Connection findConnectionBetween(String requesterProfileId, String targetProfileId);
}
