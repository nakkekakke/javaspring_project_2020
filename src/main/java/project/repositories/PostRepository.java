package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
