package com.practice.poll.practice.repository;

import com.practice.poll.practice.model.ChoiceVoteCount;
import com.practice.poll.practice.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//Note that Many of the queries cannot be invoked using the Spring-Data-Jpa’s dynamic query methods.
//Even if they can be invoked, they don’t generate an optimized query,they don't fire it directly from where
// they suppose to fire them.

//we’re using JPQL instead of JPA constructor expression in some of the queries to return the query result
// in the form of
// a custom class called ChoiceVoteCount
@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
   // The ChoiceVoteCount class is used in VoteRepository to return custom results from the query


    //evoke from the choice count class the choice and the id  of the voter in the Vote class and group by id
    @Query("SELECT NEW com.practice.poll.practice.model.ChoiceVoteCount(v.choice.id,count(v.id)) FROM Vote" +
            "v WHERE v.poll.id = :pollId GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds")List<Long> pollIds);

    //evoke from the choice count class the id  of the voter in the Vote class and group by id
    @Query("SELECT NEW com.practice.poll.practice.model.ChoiceVoteCount(v.choice.id, count(v.id)) FROM Vote v " +
            "WHERE v.poll.id = :pollId GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId")  Long pollIds);

    //evoke from vote where the id is...then collate the user and the vote id
    @Query("SELECT v FROM Vote v where v.user.id = :userId and v.poll.id in :pollIds")
    List<Vote> findByUserIdAndPollIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);

    @Query("SELECT v FROM Vote v where v.user.id = :userId and v.poll.id = :pollId")
    Vote findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("SELECT COUNT(v.id) from Vote v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.poll.id FROM Vote v WHERE v.user.id = :userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}

