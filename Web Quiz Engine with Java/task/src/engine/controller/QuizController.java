package engine.controller;

import engine.model.QuizCompletion;
import engine.model.User;
import engine.model.dto.QuizCreateDTO;
import engine.model.dto.QuizDTO;
import engine.model.dto.QuizResponseDTO;
import engine.model.dto.QuizSolveDTO;
import engine.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Defines a REST controller for managing quizzes. It handles various HTTP requests related to quiz operations.
 */
@RestController
@RequestMapping(value = "/api/quizzes")
@AllArgsConstructor
@Validated
public class QuizController {

    private final QuizService quizService;

    /**
     * Returns a paginated list of all quizzes.
     */
    @GetMapping
    public @ResponseBody Page<QuizDTO> getAll(@RequestParam int page) {
        return quizService.getAllPaginated(page);
    }

    /**
     * Creates a new quiz. Requires a valid and authenticated user.
     */
    @PostMapping
    public @ResponseBody QuizDTO create(@Valid @RequestBody QuizCreateDTO quizDTO, @AuthenticationPrincipal User user) {
        return quizService.create(quizDTO, user);
    }

    /**
     * Retrieves a quiz by ID.
     */
    @GetMapping("/{id}")
    public @ResponseBody QuizDTO getById(@PathVariable long id) {
        return quizService.getById(id);
    }

    /**
     * Deletes a quiz by id. Requires a valid and authenticated user.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, @AuthenticationPrincipal User user) {
        quizService.delete(id, user);
    }

    /**
     * Solves a quiz with specified id. Requires a valid and authenticated user.
     */
    @PostMapping("/{id}/solve")
    public @ResponseBody QuizResponseDTO solveQuiz(@PathVariable long id, @Valid @RequestBody QuizSolveDTO dto,
                                                   @AuthenticationPrincipal User user) {
        return quizService.solve(id, dto.getAnswer(), user.getEmail());
    }

    /**
     * Retrieves a paginated list of completed quizzes by currently authenticated user.
     */
    @GetMapping("/completed")
    public @ResponseBody Page<QuizCompletion> getCompletedQuizzes(@RequestParam int page, @AuthenticationPrincipal User user) {
        return quizService.getSolvedByUser(page, user.getId());
    }
}
