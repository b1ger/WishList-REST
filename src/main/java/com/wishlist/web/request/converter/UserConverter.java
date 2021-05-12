package com.wishlist.web.request.converter;

import com.wishlist.config.constants.AuthoritiesConstants;
import com.wishlist.model.User;
import com.wishlist.service.AuthorityService;
import com.wishlist.social.CustomOAuth2User;
import com.wishlist.util.PasswordUtils;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<CustomOAuth2User, User> {

    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserConverter(AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Synchronized
    @Override
    public User convert(CustomOAuth2User source) {
        final User user = new User();
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setEmail(source.getEmail());
        user.setPassword(this.passwordEncoder.encode(PasswordUtils.shakePasswordFromSocial(source)));
        user.setPhotoUrl(source.getPhotoUrl());
        user.setActivated(true);
        user.addAuthority(authorityService.getAuthorityByName(AuthoritiesConstants.ROLE_USER));
        return user;
    }
}
