package engine.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_completions", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "completedAt" })})
@NoArgsConstructor
@Getter
@Setter
@JsonIncludeProperties(value = {"id", "completedAt"})
public class QuizCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cid;
    @JsonProperty("id")
    private long quizId;
    private long userId;
    private LocalDateTime completedAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    public QuizCompletion(User user, int quizId, LocalDateTime completedAt) {
        this.user = user;
        this.userId = user.getId();
        this.quizId = quizId;
        this.completedAt = completedAt;
    }

}
