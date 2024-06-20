package site.doto.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.todo.entity.Todo;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {
    Optional<Todo> findById(Long todoId);

    @Modifying
    @Transactional
    @Query("delete from Todo t " +
            "where t.category.id = :categoryId")
    void deleteByCategoryId(@Param("categoryId") Long categoryId);

}
