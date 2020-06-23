package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.PictureObject;

public interface PictureObjectRepository extends JpaRepository<PictureObject, Long> {
    
}
