package engine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizCreateDTO extends QuizDTO {
    // can be empty or null
    private Set<Integer> answer;
}
