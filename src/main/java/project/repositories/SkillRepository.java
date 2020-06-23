package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    
}
