package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class QuizDTO {
    private String title;
    private String text;
    private List<String> options;
}
