package engine.service;

import engine.exceptions.CustomExceptions;
import engine.model.Quiz;
import engine.model.QuizCompletion;
import engine.model.User;
import engine.model.dto.QuizCreateDTO;
import engine.model.dto.QuizDTO;
import engine.model.dto.QuizResponseDTO;
import engine.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Provides business logic operations for managing quizzes. It interacts with the QuizRepository and other services
 *  to perform CRUD operations.
 */
@Service
@AllArgsConstructor
public class QuizService {

    private final Logger appLogger;
    private final ModelMapper modelMapper;

    private final QuizRepository quizRepository;
    private final UserService userService;

    private final int MAX_PAGE_ELEMENTS = 10;

    /**
     * Retrieves a paginated list of all quizzes.
     * @param page The zero-based index of the page to retrieve
     * @return Page object containing required page of quizzes
     */
    public Page<QuizDTO> getAllPaginated(int page) {
        return quizRepository
                .findAllPaginated(PageRequest.of(page, MAX_PAGE_ELEMENTS)) // fetch a page of quizzes from the repo
                .map(q -> modelMapper.map(q, QuizDTO.class)); // map each Quiz to QuizDTO
    }

    /**
     * Retrieves a quiz by id.
     */
    public QuizDTO getById(long quizId) {
        return modelMapper.map(getByIdInternal(quizId), QuizDTO.class);
    }

    /**
     * Retrieves a paginated list of quizzes completed by a specific user
     * @param page The zero-based index of the page to retrieve
     * @param userId The user identifier
     * @return A Page object containing the requested page of completed quizzes (QuizCompletion) for the user
     */
    public Page<QuizCompletion> getSolvedByUser(int page, long userId) {
        return quizRepository.findSolvedByUser(userId, PageRequest.of(page, MAX_PAGE_ELEMENTS));
    }

    /**
     * Creates a new quiz
     * @param quizDTO DTO object containing the quiz information to create.
     * @param user The authenticated user who is creating the quiz.
     * @return A QuizDTO object representing the created quiz.
     */
    @Transactional
    public QuizDTO create(QuizCreateDTO quizDTO, User user) {
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class); // map the dto to quiz
        quiz.setUser(user); // update author
        quiz = quizRepository.save(quiz); // save in repository
        appLogger.info("Created new quiz with id {}", quiz.getId());
        return modelMapper.map(quiz, QuizDTO.class); // map the created quiz back to dto and return
    }

    /**
     * Deletes a quiz by its identifier.
     * @param quizId The quiz identifier
     * @param user The authenticated user attempting to delete the quiz
     */
    @Transactional
    public void delete(long quizId, User user) {
        Quiz quiz = getByIdInternal(quizId); // retrieve the quiz by id
        if (!quiz.getUser().getEmail().equals(user.getEmail())) // check if user is the author
            throw new AccessDeniedException("You do not have permission to delete this quiz");
        quizRepository.delete(quiz); // delete from repository
        appLogger.info("Deleted quiz with id {}", quizId);
    }

    /**
     * Attempts to solve a quiz and provides feedback to the user.
     */
    @Transactional
    public QuizResponseDTO solve(long quizId, Set<Integer> clientAnswers, String userEmail) {
        Quiz quiz = getByIdInternal(quizId); // retrieve the quiz by id
        boolean solved = isSolved(clientAnswers, quiz); // check if solved
        // update users completed quizzes
        if (solved) {
            User user = userService.getUser(userEmail); // retrieve user from service, since lazily fetched
            // create quiz completion and add to user's completed quizzes list
            user.addCompletion(new QuizCompletion(user, quizId, LocalDateTime.now()));
            appLogger.info("User {} solved quiz with id {}", userEmail, quizId);
        }
        return new QuizResponseDTO(
                solved,
                solved ? "Congratulations, you're right!" : "Wrong answer! Please try again.");
    }

    // Check is answers provided by the client match the quiz answers.
    private static boolean isSolved(Set<Integer> clientAnswers, Quiz quiz) {
        Set<Integer> answers = quiz.getAnswer();
        return (answers != null && !answers.isEmpty())
                ? answers.size() == clientAnswers.size() && answers.containsAll(clientAnswers)
                : clientAnswers.isEmpty();
    }

    // Retrieve quiz with specified id from the quiz repository.
    private Quiz getByIdInternal(long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(CustomExceptions.QuizNotFoundException::new);
    }
}
