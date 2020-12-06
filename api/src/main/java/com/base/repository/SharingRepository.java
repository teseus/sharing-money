package com.base.repository;

import com.base.entity.Sharing;
import com.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SharingRepository extends JpaRepository<Sharing, Long> {
    List<Sharing> findByUserAndCreatedAtBetween(
            final User user, final LocalDateTime startAt, final LocalDateTime endAt);

    List<Sharing> findByUserAndRoomIdAndToken(final User user, final String roomId, final String token);
}
