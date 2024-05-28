package engine.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a user entity in the persistence layer. It maps to the "users" table in the database.
 */
@Entity
@Table(name = "users")
@Data
@JsonIncludeProperties({"id", "email", "enabled"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "user_id")
    private long id;
    private String email;

    @ToString.Exclude
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Quiz> createdQuizzes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizCompletion> completedQuizzes = new ArrayList<>();

    public void addCompletion(QuizCompletion quizCompletion) {
        quizCompletion.setUser(this);
        completedQuizzes.add(quizCompletion);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
