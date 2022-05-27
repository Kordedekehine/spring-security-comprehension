package com.practice.poll.practice.controller;

//ROLE OF THE USER CONTROLLER
//Get the currently logged in user.
//Check if a username is available for registration.
//Check if an email is available for registration.
//Get the public profile of a user.
//Get a paginated list of polls created by a given user.
//Get a paginated list of polls in which a given user has voted.

import com.practice.poll.practice.exception.ResourceNotFoundException;
import com.practice.poll.practice.model.User;
import com.practice.poll.practice.payload.ResponsePayload.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollService pollService;

  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/user/info")
  @PreAuthorize("hasRole('USER')")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal activeUser){
      UserSummary userSummary = new UserSummary(activeUser.getId(), activeUser.getName(), activeUser.getUsername());
      return userSummary;
  }

  @GetMapping("/user/checkUserAvailabilityStatus")
  public UserIdAvailability checkUserAvailabilityStatus(@RequestParam(value = "username") String username){
      Boolean isUserAvailable = !userRepository.existsByUsername(username);
      return new UserIdAvailability(isUserAvailable);
  }

  @GetMapping("/users/checkUserEmailAvailabilityStatus")
  public UserIdAvailability checkUserEmailAvailabilityStatus(@RequestParam(value = "email") String email){
      Boolean isUserAvailable = !userRepository.existsByEmail(email);
      return new UserIdAvailability(isUserAvailable);
  }

  public UserProfile checkUserProfile(@PathVariable (value = "username") String username){
      User user = userRepository.findByUsername(username)
              .orElseThrow( () -> new ResourceNotFoundException("user","username",username));

      long pollTracker = pollRepository.countByCreatedBy(user.getId());
      long voteTracker = voteRepository.countByUserId(user.getId());

      UserProfile userProfile = new UserProfile(user.getId(), user.getName(),user.getUsername(),user.getCreatedAt()
      ,pollTracker,voteTracker);

      return userProfile;
  }

  @GetMapping("/users/{username}/polls")
   public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable (value = "username") String username,
                                                        @CurrentUser UserPrincipal activeUser,
                                                        @RequestParam(value = "page",defaultValue = AppConstants
                                                                .DEFAULT_PAGE_NUMBER) int page,
                                                        @RequestParam(value = "size",defaultValue = AppConstants
                                                                .DEFAULT_PAGE_SIZE) int size){
      return pollService.getPollsCreatedBy(username,activeUser,page,size);
   }

   public PagedResponse<PollResponse> getVotePollsCastedBy(@PathVariable (value = "username") String username,
                                                           @CurrentUser UserPrincipal activeUser,
                                                           @RequestParam(value = "page",defaultValue = AppConstants
                                                                   .DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size",defaultValue = AppConstants
                                                                   .DEFAULT_PAGE_SIZE) int size){
      return pollService.getPollsVotedBy(username,activeUser,page,size);
   }
}
