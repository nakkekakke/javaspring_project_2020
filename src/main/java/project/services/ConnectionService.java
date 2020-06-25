package project.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.domain.Connection;
import project.repositories.AccountRepository;
import project.repositories.ConnectionRepository;

@Service
public class ConnectionService {
    
    @Autowired
    private ConnectionRepository connectionRepo;
    
    public List<Connection> getConnectionsOfTarget(String profileId, boolean confirmed) {
        return connectionRepo.findByTargetProfileIdAndConfirmed(profileId, confirmed);
    }
     
    public Connection getConnectionStartedBy(String requesterProfileId, String targetProfileId) {
        return connectionRepo.findByRequesterProfileIdAndTargetProfileId(requesterProfileId, targetProfileId);
    }
    
    public Connection getConnectionBetween(String requesterProfileId, String targetProfileId) {
        return connectionRepo.findConnectionBetween(requesterProfileId, targetProfileId);
    }

    public List<Connection> getConnectionsOf(String profileId) {
        return connectionRepo.findByConfirmedAndRequesterProfileIdOrConfirmedAndTargetProfileId(profileId, profileId, true);
    }
    
    @Transactional
    public void answerConnection(String requesterProfileId, String targetProfileId, boolean confirm) {
        Connection connection = getConnectionStartedBy(requesterProfileId, targetProfileId);
        if (confirm) {
            connection.setConfirmed(true);
        } else {
            String deletionId = connection.getRequesterProfileId();
            connectionRepo.delete(connection);
        }
    }
    
    @Transactional
    public void requestConnection(String requesterProfileId, String targetProfileId) {
        Connection connection = new Connection(requesterProfileId, targetProfileId, false);
        connectionRepo.save(connection);
    }
    
}
