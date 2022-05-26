package com.practice.poll.practice.payload.ResponsePayload;

import java.time.Instant;

public class UserProfile {

    private Long id;
    private String name;
    private String username;
    private Instant joinedAt;
    private Long PollCount;
    private Long VoteCount;

    public UserProfile(Long id, String name, String username, Instant joinedAt, Long pollCount, Long voteCount) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.joinedAt = joinedAt;
        PollCount = pollCount;
        VoteCount = voteCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getPollCount() {
        return PollCount;
    }

    public void setPollCount(Long pollCount) {
        PollCount = pollCount;
    }

    public Long getVoteCount() {
        return VoteCount;
    }

    public void setVoteCount(Long voteCount) {
        VoteCount = voteCount;
    }
}
