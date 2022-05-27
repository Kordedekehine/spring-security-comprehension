package com.practice.poll.practice.payload.util;

import com.practice.poll.practice.model.ChoiceVoteCount;
import com.practice.poll.practice.model.Poll;
import com.practice.poll.practice.model.User;
import com.practice.poll.practice.payload.ResponsePayload.ChoiceResponse;
import com.practice.poll.practice.payload.ResponsePayload.PollResponse;
import com.practice.poll.practice.payload.ResponsePayload.UserSummary;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//First,we need to consider our client side-for the poll-the MUST KNOWN POLL DATAS NEED TO BE MAPPED 2 DISPLAY
//We’ll be mapping the Poll entity to a PollResponse payload which contains a bunch of information like
// Poll’s creator name, Vote counts of each choice in the Poll, the choice that the currently logged in user
// has voted for

public class ModelMapper {

    public static PollResponse mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap,
                                                     User creator, Long userVote) {
        PollResponse pollResponse = new PollResponse();
       pollResponse.setId(poll.getId());
       pollResponse.setQuestion(poll.getQuestion());
       pollResponse.setCreationDateTime(poll.getCreatedAt());
       pollResponse.setExpirationDateTime(poll.getExpirationDateAndTime());
        Instant now = Instant.now();
        pollResponse.setExpired(poll.getExpirationDateAndTime().isBefore(now));



        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponse choiceResponse = new ChoiceResponse();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());

            if (choiceVotesMap.containsKey(choice.getId())){
                choiceResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                choiceResponse.setVoteCount(0);
            }
            return choiceResponse;
        }).collect(Collectors.toList()); //if get the identity of the voter COUNT
        //if not set vote to 0 or just go back

        pollResponse.setChoices(choiceResponses);  //collect the voter infos
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getName(), creator.getUsername());
        pollResponse.setCreatedBy(creatorSummary);


        if (userVote != null){ //if voter is not equal to null..VOTED..else ignore and count the total votes
            pollResponse.setSelectedChoice(userVote);
        }
    long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponse::getId).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }
}
