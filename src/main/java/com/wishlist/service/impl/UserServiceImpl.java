package com.wishlist.service.impl;

import com.wishlist.config.constants.AuthoritiesConstants;
import com.wishlist.exception.EmailAlreadyUsedException;
import com.wishlist.exception.InvalidPasswordException;
import com.wishlist.exception.NotFoundException;
import com.wishlist.model.User;
import com.wishlist.repository.AuthorityRepository;
import com.wishlist.repository.UserRepository;
import com.wishlist.service.UserService;
import com.wishlist.social.CustomOAuth2User;
import com.wishlist.social.Provider;
import com.wishlist.util.PasswordUtils;
import com.wishlist.util.RandomUtil;
import com.wishlist.web.request.UserRequest;
import com.wishlist.web.request.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            AuthorityRepository authorityRepository,
            PasswordEncoder passwordEncoder,
            UserConverter userConverter
    ) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
    }

    @Override
    public User findById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("User with id:%s, does not exist", id))
        );
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        return userRepository.findOneByEmailIgnoreCase(email).orElseThrow(
                () -> new NotFoundException(String.format("User with email: %s, does not exist", email))
        );
    }

    @Override
    public User createUser(UserRequest request, boolean isActive) throws EmailAlreadyUsedException {

        if (!PasswordUtils.checkPasswordLength(request.getPassword())) {
            throw new InvalidPasswordException();
        }
        this.userRepository.findOneByEmailIgnoreCase(request.getEmail())
                .ifPresent(user -> {
                    throw new EmailAlreadyUsedException();
                });

        final User user = new User();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setProvider(Provider.LOCAL);

        String encryptedPassword = this.passwordEncoder.encode(request.getPassword());
        user.setPassword(encryptedPassword);
        this.authorityRepository.findByName(AuthoritiesConstants.ROLE_USER).ifPresent(user::addAuthority);

        if (isActive) {
            user.setActivated(true);
        } else {
            user.setActivated(false);
            user.setActivationKey(RandomUtil.generateActivationKey());
        }

        return this.userRepository.save(user);
    }

    @Override
    public User createSocialUser(CustomOAuth2User socialUser) {
        final User detachedUser = userConverter.convert(socialUser);
        return this.userRepository.save(Objects.requireNonNull(detachedUser));
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
