package com.practice.poll.practice.payload.requestPayload;

import javax.validation.constraints.NotNull;

public class VoteRequest {

    @NotNull
    private Long ChoiceId;

    public Long getChoiceId() {
        return ChoiceId;
    }

    public void setChoiceId(Long choiceId) {
        ChoiceId = choiceId;
    }
}
