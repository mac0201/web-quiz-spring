package engine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * This class extends the {@code QuizDTO} and is specifically used for creating new quizzes. It inherits all properties from
 *  QuizDTO and adds and additional {@code answer} property to specify the correct answers.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizCreateDTO extends QuizDTO {
    // This set can be null or empty since quizzes are allowed to have no correct answer.
    private Set<Integer> answer;
}
