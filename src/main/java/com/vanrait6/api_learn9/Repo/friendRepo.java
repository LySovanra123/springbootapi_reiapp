package com.vanrait6.api_learn9.Repo;

import com.vanrait6.api_learn9.Model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface friendRepo extends JpaRepository<Friend,Integer> {

    Friend findByEmailFollowerAndEmailFollowing(
            String email_follower,
            String email_following
    );

    @Query("""
        SELECT f
        FROM Friend f
        WHERE f.emailFollower = :me OR f.emailFollowing = :me
    """)
    List<Friend> findAllRelated(String me);

    @Query("""
    SELECT COUNT(f1)
    FROM Friend f1, Friend f2
    WHERE f1.emailFollower = :email
      AND f2.emailFollower = f1.emailFollowing
      AND f2.emailFollowing = f1.emailFollower
    """)
    long getFriendCount(String email);
}
