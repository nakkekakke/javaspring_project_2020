package project.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Comment extends AbstractPersistable<Long> {
    
    private String message;
    private LocalDate createdAt;
    
    @ManyToOne
    private Account creator;
    
    @ManyToOne
    private Post post;
}
