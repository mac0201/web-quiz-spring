package engine.controller;

import engine.model.QuizDTO;
import engine.model.QuizResponseDTO;
import engine.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/quiz")
@AllArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody QuizDTO getQuiz() {
        return quizService.getQuiz();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody QuizResponseDTO submitAnswer(@RequestParam int answer) {
        return quizService.submitAnswer(answer);
    }

}
