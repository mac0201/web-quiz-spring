package engine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Data Transfer Object for responses related to quiz attempts. It indicates the outcome and provides
 *  optional feedback to the user
 */
@AllArgsConstructor
@Setter
@Getter
public class QuizResponseDTO {
    private boolean success;
    private String feedback;
}
