package com.practice.poll.practice.Config;

import com.practice.poll.practice.model.User;
import com.practice.poll.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
      //Here we allow people to log in with their email or username
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).
                orElseThrow( () -> new UsernameNotFoundException("USER NOT FOUND USING EMAIL" +
                        "AND USERNAME"));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("USER WITH ID " +id + " CANNOT BE FOUND")
        );
        return UserPrincipal.create(user);
    }
}
