package com.wardrobe.config;

import com.wardrobe.repository.UserRepository;
import com.wardrobe.service.security.DomainUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsSecurityConfig {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService()  {
        return new DomainUserDetailService(userRepository);
    }
}
