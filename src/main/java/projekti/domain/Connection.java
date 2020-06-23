package projekti.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity(name="ACCOUNT_CONNECTION")
@AllArgsConstructor @NoArgsConstructor @Data
public class Connection extends AbstractPersistable<Long> {
    
    private Long requesterId;
    private Long receiverId;
    private boolean confirmed;
}
