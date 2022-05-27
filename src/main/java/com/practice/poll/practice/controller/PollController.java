package com.practice.poll.practice.controller;

import com.practice.poll.practice.repository.PollRepository;
import com.practice.poll.practice.repository.UserRepository;
import com.practice.poll.practice.repository.VoteRepository;
import com.practice.poll.practice.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollService pollService;



}
