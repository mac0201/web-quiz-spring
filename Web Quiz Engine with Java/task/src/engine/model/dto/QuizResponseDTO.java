package engine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class QuizResponseDTO {
    private boolean success;
    private String feedback;
}
