package com.practice.poll.practice.repository;

import com.practice.poll.practice.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PollRepository extends JpaRepository<Poll,Long> {
    @Override
    Optional<Poll> findById(Long PollId);

    Page<Poll> findCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long UserId);

    List<Poll> findByIdIn(List<Long> Polls);

    List<Poll> findByIdIn(List<Long> Polls, Sort sort);
}
