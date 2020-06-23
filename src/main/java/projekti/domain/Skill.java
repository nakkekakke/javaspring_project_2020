package projekti.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Skill extends AbstractPersistable<Long> {
    
    private String name;
    
    @ManyToOne
    private Account owner;
    
    @OneToMany
    private List<Account> praisers = new ArrayList<>();
}
