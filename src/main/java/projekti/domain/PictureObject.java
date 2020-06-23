package projekti.domain;
import javax.persistence.Entity;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
class PictureObject extends AbstractPersistable<Long> {
    
    @Lob
    private byte[] content;
}
