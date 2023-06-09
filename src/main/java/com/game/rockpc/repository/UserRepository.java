package com.game.rockpc.repository;


import com.game.rockpc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginAndPassword(@NonNull String login, @NonNull String password);
    Optional<User> findByLogin(@NonNull String login);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE users
             SET last_auth_timestamp=NOW()
             WHERE id = :id
            """, nativeQuery = true)
    void updateLastAuthTimestamp(@Param(value = "id") long id);
}
