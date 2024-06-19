package site.doto.domain.friend.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import site.doto.domain.friend.entity.Friend;
import site.doto.domain.friend.entity.FriendPK;
import site.doto.domain.friend.enums.FriendRelation;

public interface FriendRepository extends JpaRepository<Friend, FriendPK> {
    @Modifying
    @Query("update Friend f set f.status = :status where f.friendPK = :friendPK")
    void updateFriendRelation(@Param("friendPK") FriendPK friendPK, @Param("status") FriendRelation status);
}
