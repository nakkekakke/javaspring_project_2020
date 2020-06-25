package project.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Account extends AbstractPersistable<Long> {
    
    @NotEmpty
    @Size(min=5, max=50)
    private String name;
    
    @NotEmpty
    @Size(min=5, max=50)
    private String username;
    
    @NotEmpty
    private String password;
    
    @NotEmpty
    private String profileId;
    
    @OneToOne
    private PictureObject profilePicture;
    
    @OneToMany(mappedBy = "owner")
    private List<Skill> skills = new ArrayList<>();
    
    @OneToMany(mappedBy = "creator")
    private List<Post> posts = new ArrayList<>();
}
