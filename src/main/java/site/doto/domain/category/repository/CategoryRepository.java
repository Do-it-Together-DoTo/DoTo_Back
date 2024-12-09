package site.doto.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.doto.domain.category.entity.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findById(Long categoryId);

    @Query("select coalesce(max(c.seq), 0)" +
            "from Category c " +
            "where c.member.id = :memberId " +
            "and c.isActivated = :isActivated")
    Integer categorySeqByMemberId(@Param("memberId") Long memberId, @Param("isActivated") Boolean isActivated);

    @Query("select count(c) " +
            "from Category  c " +
            "where c.member.id = :memberId " +
            "and c.isActivated = true")
    Integer countCategoryByMemberId(@Param("memberId") Long memberId);

    @Query("select c " +
            "from Category c " +
            "where c.member.id = :memberId " +
            "order by c.seq")
    List<Category> findCategoriesByMemberId(@Param("memberId") Long memberId);

    @Query("select c " +
            "from Category c " +
            "where c.member.id = :memberId and c.isActivated = true " +
            "order by c.seq")
    List<Category> findCategoriesByMemberIdAndIsActivated(@Param("memberId") Long memberId);

    @Query("select c " +
            "from Category c right join Todo t " +
            "on c.id = t.category.id " +
            "where c.member.id = :memberId " +
            "and t.date = :date " +
            "order by c.seq")
    List<Category> findCategoriesByTodoIsPresent(@Param("memberId") Long memberId,
                                                 @Param("date") LocalDate date);
}
