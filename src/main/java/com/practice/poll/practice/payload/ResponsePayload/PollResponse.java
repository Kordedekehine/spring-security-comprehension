package com.practice.poll.practice.payload.ResponsePayload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

public class PollResponse {
    private Long id;
    private String question;
    private List<ChoiceResponse> choices;
    private UserSummary createdBy;
    private Instant CreationDateTime;
    private Instant ExpirationDateTime;
    private Boolean isExpired;

    @JsonInclude(JsonInclude.Include.NON_NULL) //@JsonInclude annotation provides Include.NON_NULL attribute to
    // ignore fields with Null values and Include.NON_EMPTY attribute to ignore fields with Empty values.
    private Long selectedChoice;
    private Long totalVotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceResponse> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceResponse> choices) {
        this.choices = choices;
    }

    public UserSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreationDateTime() {
        return CreationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        CreationDateTime = creationDateTime;
    }

    public Instant getExpirationDateTime() {
        return ExpirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        ExpirationDateTime = expirationDateTime;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Long getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(Long selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    public Long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
    }
}
