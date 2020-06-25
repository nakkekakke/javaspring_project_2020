package project.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="ACCOUNTCONNECTION")
@AllArgsConstructor @NoArgsConstructor @Data
public class Connection extends AbstractPersistable<Long> {
    
    private String requesterProfileId;
    private String targetProfileId;
    private boolean confirmed;
}
