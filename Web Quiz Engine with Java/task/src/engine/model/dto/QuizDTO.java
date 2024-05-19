package engine.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class QuizDTO {
    private int id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @NotNull
    @Size(min = 2)
    private List<String> options;
}
