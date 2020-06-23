package projekti.domain;

import java.time.LocalDate;
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
public class Post extends AbstractPersistable<Long> {
    
    private String message;
    private LocalDate createdAt;
    
    @ManyToOne
    private Account creator;
    
    @OneToMany
    private List<Account> likers = new ArrayList<>();
    
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}
