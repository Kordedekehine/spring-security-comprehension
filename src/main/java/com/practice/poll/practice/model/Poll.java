package com.practice.poll.practice.model;

import com.practice.poll.practice.model.audit.UserDateAudit;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polls") //talking about poll we want to choose or vote for a particular stuffs
public class Poll extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String question;


    @OneToMany(
            mappedBy = "poll",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true //The orphanRemoval attribute is going to instruct the JPA provider to trigger a
            // remove entity
            // state transition when a PostComment entity is no longer referenced by its parent Post entity
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
   private List<Choice> choices = new ArrayList<>();
    @NotNull
    private Instant expirationDateAndTime;


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

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Instant getExpirationDateAndTime() {
        return expirationDateAndTime;
    }

    public void setExpirationDateAndTime(Instant expirationDateAndTime) {
        this.expirationDateAndTime = expirationDateAndTime;
    }

    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setPoll(this);
    }

    public void removeChoice(Choice choice) {
        choices.remove(choice);
        choice.setPoll(null);
    }
}
