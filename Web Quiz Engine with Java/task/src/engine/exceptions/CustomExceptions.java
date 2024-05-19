package engine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomExceptions {

    public static class ServiceException extends RuntimeException {}

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Quiz with specified ID not found!")
    public static class QuizNotFoundException extends ServiceException {}

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Missing body parameters")
    public static class BodyParametersInvalidException extends ServiceException {}




}
