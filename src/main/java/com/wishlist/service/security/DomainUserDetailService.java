package com.wishlist.service.security;

import com.wishlist.exception.UserNotActivatedException;
import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainUserDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(DomainUserDetailService.class);

    private final UserRepository userRepository;

    @Autowired
    public DomainUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Authenticating {}", email);
        final String lowercaseEmail = email.toLowerCase(Locale.ENGLISH);
        final Optional<User> userFromDatabase = this.userRepository.findOneWithAuthoritiesByEmailIgnoreCase(lowercaseEmail);
        return userFromDatabase.map(user -> {
            if (!user.isActivated()) {
                throw new UserNotActivatedException(String.format("User \"%s\" was not activated", lowercaseEmail));
            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseEmail,
                    user.getPassword(),
                    grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException(String.format("User \"%s\" was not found in the database", lowercaseEmail)));
    }
}
