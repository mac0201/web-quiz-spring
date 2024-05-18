package engine.service;

import engine.exceptions.CustomExceptions;
import engine.model.Quiz;
import engine.model.dto.QuizCreateDTO;
import engine.model.dto.QuizResponseDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizService {

    private final Logger appLogger;
    private final ModelMapper modelMapper;
    private final List<Quiz> quizRepository = Collections.synchronizedList(new ArrayList<>());

    public List<Quiz> getAll() {
        return quizRepository;
    }

    public Quiz getById(int id) {
        if (id < 0 || id >= quizRepository.size()) throw new CustomExceptions.QuizNotFoundException();
        return quizRepository.get(id);
    }

    public Quiz create(QuizCreateDTO quizDTO) {
        int nextId = quizRepository.size();
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        quiz.setId(nextId);
        quizRepository.add(quiz);
        appLogger.info("Created new quiz with id {}", nextId);
        return quiz;
    }

    public QuizResponseDTO solve(int quizId, int answer) {
        try {
            Quiz quiz = quizRepository.get(quizId);
            boolean solved = quiz.getAnswer() == answer;
            return new QuizResponseDTO(
                    solved,
                    solved ? "Congratulations, you're right!" : "Wrong answer! Please try again.");
        } catch (IndexOutOfBoundsException e) {
            throw new CustomExceptions.QuizNotFoundException();
        }
    }
}
