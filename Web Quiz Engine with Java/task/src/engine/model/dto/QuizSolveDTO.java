package engine.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class QuizSolveDTO {
    private Set<Integer> answer;
}
