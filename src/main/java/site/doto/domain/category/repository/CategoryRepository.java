package site.doto.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.doto.domain.category.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long categoryId);

    @Query("select count(*) " +
    "from Category c " +
    "where c.member.id = :memberId " +
    "and c.isActivated = true")
    int countByMemberId(@Param("memberId") Long memberId);

    @Query("select c " +
    "from Category c " +
    "where c.member.id = :memberId " +
    "and c.isActivated = true")
    List<Category> findCategoriesByIsActivated(@Param("memberId") Long memberId);

    @Query("select c " +
            "from Category c " +
            "where c.member.id = :memberId " +
            "and c.isActivated = false")
    List<Category> findCategoriesByIsInactivated(@Param("memberId") Long memberId);

}
