package engine.service;

import engine.model.QuizDTO;
import engine.model.QuizResponseDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizService {

    private final Logger appLogger;

    private final List<QuizDTO> quizDTOList = List.of(new QuizDTO(
            "The Java Logo",
            "Test text",
            List.of("A1", "A2", "A3", "A4")));

    public QuizDTO getQuiz() {
        return quizDTOList.get(0);
    }

    public QuizResponseDTO submitAnswer(int index) {
        boolean success = index == 2;
        return new QuizResponseDTO(
                success,
                success ? "Congratulations, you're right!" : "Wrong answer! Please try again.");
    }

}
