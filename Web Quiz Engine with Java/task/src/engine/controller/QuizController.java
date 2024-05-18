package engine.controller;

import engine.model.Quiz;
import engine.model.dto.QuizCreateDTO;
import engine.model.dto.QuizResponseDTO;
import engine.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/quizzes")
@AllArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public @ResponseBody List<Quiz> getAll() {
        return quizService.getAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Quiz getById(@PathVariable int id) {
        return quizService.getById(id);
    }

    @PostMapping
    public @ResponseBody Quiz create(@RequestBody QuizCreateDTO quizDTO) {
        return quizService.create(quizDTO);
    }

    @PostMapping("{id}/solve")
    public @ResponseBody QuizResponseDTO solveQuiz(@PathVariable int id, @RequestParam int answer) {
        return quizService.solve(id, answer);
    }
}
