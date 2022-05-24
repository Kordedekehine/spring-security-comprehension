package com.practice.poll.practice.Config;

import com.practice.poll.practice.security.UserPrincipal;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig { //To configure the update and createdBy and to populate it

    public AuditorAware<Long> auditorInput (){
        return new SpringSecurityAwareImpl();
    }

     class SpringSecurityAwareImpl implements AuditorAware<Long>{

         @Override
         public Optional<Long> getCurrentAuditor() {
             //confirm the current auditor security details
             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

             if (authentication == null || !authentication.isAuthenticated() ||
             authentication instanceof AnonymousAuthenticationToken){
                 return Optional.empty();
                 //not correct! then return nothing
             }

             UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
             return Optional.ofNullable(userPrincipal.getId());
             //correct! then give access
         }
     }
}
