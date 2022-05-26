package com.practice.poll.practice.payload.requestPayload;

import javax.validation.constraints.NotBlank;


public class ChoiceRequest {
    @NotBlank
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
