package engine.repository;

import engine.model.Quiz;
import engine.model.QuizCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long>, PagingAndSortingRepository<Quiz, Long> {
    /**
     * Finds all quizzes with pagination.
     */
    @Query("SELECT q FROM Quiz q")
    Page<Quiz> findAllPaginated(Pageable pageable);

    /**
     * Finds quizzes completed by a specific user with pagination.
     */
    @Query("SELECT q FROM QuizCompletion q WHERE q.userId = :id ORDER BY q.completedAt DESC")
    Page<QuizCompletion> findSolvedByUser(@Param("id") long userId, Pageable pageable);
}
