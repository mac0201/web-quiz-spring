package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Represents a quiz entity in the persistence layer. It maps to the "quizzes" table in the database.
 */
@Entity
@Table(name = "quizzes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quiz {
    @Id
    @GeneratedValue(generator = "quiz_seq")
    @SequenceGenerator(name = "quiz_seq", allocationSize = 1, initialValue = 1)
    private long id;
    private String title;
    private String text;
    @ElementCollection
    @Fetch(FetchMode.SUBSELECT)
    private List<String> options;
    @JsonIgnore
    @ElementCollection
    private Set<Integer> answer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
