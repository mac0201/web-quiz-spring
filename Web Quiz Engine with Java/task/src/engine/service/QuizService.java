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

@Service
@AllArgsConstructor
public class QuizService {

    private final Logger appLogger;
    private final ModelMapper modelMapper;
    private final QuizRepository quizRepository;
    private final int MAX_PAGE_ELEMENTS = 10;
    private final UserService userService;

    public Page<QuizDTO> getAllPaginated(int page) {
        return quizRepository
                .findAllPaginated(PageRequest.of(page, MAX_PAGE_ELEMENTS))
                .map(q -> modelMapper.map(q, QuizDTO.class));
    }

    public QuizDTO getById(long quizId) {
        return modelMapper.map(getByIdInternal(quizId), QuizDTO.class);
    }

    public Page<QuizCompletion> getSolvedByUser(int page, long userId) {
        return quizRepository.findSolvedByUser(userId, PageRequest.of(page, MAX_PAGE_ELEMENTS));
    }

    @Transactional
    public QuizDTO create(QuizCreateDTO quizDTO, User user) {
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        quiz.setUser(user);
        quiz = quizRepository.save(quiz);
        appLogger.info("Created new quiz with id {}", quiz.getId());
        return modelMapper.map(quiz, QuizDTO.class);
    }

    @Transactional
    public void delete(long quizId, User user) {
        Quiz quiz = getByIdInternal(quizId);
        if (!quiz.getUser().getEmail().equals(user.getEmail()))
            throw new AccessDeniedException("You do not have permission to delete this quiz");
        quizRepository.delete(quiz);
        appLogger.info("Deleted quiz with id {}", quizId);
    }

    @Transactional
    public QuizResponseDTO solve(int quizId, Set<Integer> clientAnswers, String userEmail) {
        Quiz quiz = getByIdInternal(quizId);
        boolean solved = isSolved(clientAnswers, quiz);
        // update users completed quizzes
        if (solved) {
            User user = userService.getUser(userEmail);
            user.addCompletion(new QuizCompletion(user, quizId, LocalDateTime.now()));
            appLogger.info("User {} solved quiz with id {}", userEmail, quizId);
        }
        return new QuizResponseDTO(
                solved,
                solved ? "Congratulations, you're right!" : "Wrong answer! Please try again.");
    }

    private static boolean isSolved(Set<Integer> clientAnswers, Quiz quiz) {
        Set<Integer> answers = quiz.getAnswer();
        return (answers != null && !answers.isEmpty())
                ? answers.size() == clientAnswers.size() && answers.containsAll(clientAnswers)
                : clientAnswers.isEmpty();
    }

    private Quiz getByIdInternal(long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(CustomExceptions.QuizNotFoundException::new);
    }
}
