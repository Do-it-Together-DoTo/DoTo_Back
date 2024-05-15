package site.doto.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.category.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(long categoryId);
}
