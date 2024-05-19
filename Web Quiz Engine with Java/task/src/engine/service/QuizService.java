package engine.service;

import engine.exceptions.CustomExceptions;
import engine.model.Quiz;
import engine.model.dto.QuizCreateDTO;
import engine.model.dto.QuizResponseDTO;
import engine.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class QuizService {

    private final Logger appLogger;
    private final ModelMapper modelMapper;

    private final QuizRepository quizRepository;

    public List<Quiz> getAll() {
        return Lists.from(quizRepository.findAll().iterator());
    }

    public Quiz getById(long quizId) {
        return quizRepository.findById(quizId).orElseThrow(CustomExceptions.QuizNotFoundException::new);
    }

    public Quiz create(QuizCreateDTO quizDTO) {
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        quiz = quizRepository.save(quiz);
        appLogger.info("Created new quiz with id {}", quiz.getId());
        return quiz;
    }

    public QuizResponseDTO solve(int quizId, Set<Integer> clientAnswers) {
        Quiz quiz = getById(quizId);
        Set<Integer> answers = quiz.getAnswer();
        boolean solved;
        if (answers != null && !answers.isEmpty()) {
            // quiz answers not empty, so check if client answers match size and contents
            solved = answers.size() == clientAnswers.size() && answers.containsAll(clientAnswers);
        } else {
            // quiz answers null or empty, so check if client answers are also empty
            solved = clientAnswers.isEmpty();
        }
        return new QuizResponseDTO(
                solved,
                solved ? "Congratulations, you're right!" : "Wrong answer! Please try again.");
    }
}
