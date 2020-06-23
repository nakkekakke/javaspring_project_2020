package projekti.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Account extends AbstractPersistable<Long> {
    
    private String username;
    private String name;
    private String password;
    
    @OneToOne
    private PictureObject profilePicture;
    
    @OneToMany(mappedBy = "owner")
    private List<Skill> skills = new ArrayList<>();
    
    @OneToMany
    private List<Connection> connections = new ArrayList<>();
    
    @OneToMany(mappedBy = "creator")
    private List<Post> posts = new ArrayList<>();
}
