package com.game.rockpc.repository;


import com.game.rockpc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginAndPassword(@NonNull String login, @NonNull String password);

    @Modifying
    @Query("update User u set u.lastAuthTimestamp = :lastAuthTimestamp where u.id = :id")
    void updateLastAuthTimestamp(@Param(value = "id") long id, @Param(value = "lastAuthTimestamp") LocalDateTime lastAuthTimestamp);
}
