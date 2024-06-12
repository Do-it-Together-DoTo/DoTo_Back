package site.doto.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.todo.entity.Todo;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findById(Long todoId);
}
