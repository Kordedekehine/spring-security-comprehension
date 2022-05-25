package com.practice.poll.practice.repository;

import com.practice.poll.practice.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long> {

}
