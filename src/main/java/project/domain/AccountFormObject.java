package project.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class AccountFormObject {
    
    @NotEmpty(message="Name must not be empty")
    @Size(message="Name must be between 5 and 50 characters", min=5, max=50)
    private String name;
    
    @NotEmpty(message="Username must not be empty")
    @Size(message="Username must be between 5 and 50 characters", min=5, max=50)
    private String username;
    
    @NotEmpty(message="Password must not be empty")
    @Size(message="Password must be between 5 and 50 characters", min=5, max=50)
    private String password;
    
    @Size(message="Profile ID must be between 5 and 50 characters", min=5, max=50)
    private String profileId;
}
