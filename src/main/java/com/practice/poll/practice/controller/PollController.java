package com.practice.poll.practice.controller;
//ROLE OF THE POLE CONTROLLER
//Create a Poll.
//Get a paginated list of polls sorted by their creation time.
//Get a Poll by pollId.
//Vote for a choice in a poll.

import com.practice.poll.practice.model.Poll;
import com.practice.poll.practice.payload.ApiResponse;
import com.practice.poll.practice.payload.ResponsePayload.PagedResponse;
import com.practice.poll.practice.payload.ResponsePayload.PollResponse;
import com.practice.poll.practice.payload.requestPayload.PollRequest;
import com.practice.poll.practice.payload.requestPayload.VoteRequest;
import com.practice.poll.practice.payload.util.AppConstants;
import com.practice.poll.practice.repository.PollRepository;
import com.practice.poll.practice.repository.UserRepository;
import com.practice.poll.practice.repository.VoteRepository;
import com.practice.poll.practice.security.CurrentUser;
import com.practice.poll.practice.security.UserPrincipal;
import com.practice.poll.practice.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollService pollService;

    private static Logger logger = LoggerFactory.getLogger(PollController.class);

    @GetMapping
    public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal activeUser,
                                                 @RequestParam(value = "page",
                                                         defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size",
                                                 defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        //return PollService.getAllPolls(activeUser,page,size);
        return pollService.getAllPolls(activeUser,page,size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest){
        Poll poll = pollService.createPoll(pollRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true,"POLL SUCCESSFULLY CREATED!"));
        }

        @GetMapping("/{pollId}")
        public PollResponse getPollById(@CurrentUser UserPrincipal activeUser,@PathVariable Long pollId){

        return pollService.getPollById(pollId,activeUser);
    }

   public PollResponse castVote(@CurrentUser UserPrincipal activeUser, @PathVariable Long pollId,
                                  @Valid @RequestBody VoteRequest voteRequest){
     return pollService.castVoteAndGetUpdatedPoll(pollId,voteRequest,activeUser);
   }
}
