package engine.model.dto;

import lombok.*;

import java.util.Set;

/**
 * Represents a Data Transfer Object for requests made to solve quizzes.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizSolveDTO {
    // Optional
    private Set<Integer> answer;
}
