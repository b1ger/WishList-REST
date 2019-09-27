package com.wishlist.web.rest.controller;

import com.wishlist.exception.EmailAlreadyUsedException;
import com.wishlist.exception.NotFoundException;
import com.wishlist.model.Subscribition;
import com.wishlist.model.User;
import com.wishlist.service.EmailService;
import com.wishlist.service.UserService;
import com.wishlist.web.request.LoginRequest;
import com.wishlist.web.request.UserRequest;
import com.wishlist.web.response.BaseResponse;
import com.wishlist.web.rest.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = "/apiwl/user")
public class UserController {

    private UserService userService;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody UserRequest userRequest,
                                                 BindingResult bindingResult) {
        BaseResponse response = new BaseResponse();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            response.setResults(bindingResult.getAllErrors(), BaseResponse.ERROR_STATUS);
        } else {
            try {
                User user = userService.createUser(userRequest, false);
                response.setResults(user, BaseResponse.OK_STATUS);
                emailService.sendActivationEmail(user);
            } catch (EmailAlreadyUsedException ex) {
                Error errors = new Error();
                errors.addError("email", "This email already used.");
                response.setResults(errors, BaseResponse.ERROR_STATUS);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        BaseResponse response = new BaseResponse();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.error(error.toString());
            });
            setAuthError(response);
        } else {
            try {
                User user = userService.findByEmail(loginRequest.getEmail());
                if (this.passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    response.setResults(user, BaseResponse.OK_STATUS);
                } else {
                    setAuthError(response);
                }
            } catch (NotFoundException ex) {
                setAuthError(response);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/subscribe")
    public ResponseEntity<BaseResponse> subscribe(@Valid @RequestBody Subscribition subscribition,
                                                  BindingResult bindingResult) {
        BaseResponse response = new BaseResponse();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            response.setResults(bindingResult.getAllErrors(), BaseResponse.ERROR_STATUS);
        } else {
            Subscribition saved = emailService.subscribe(subscribition);
            response.setResults(saved, BaseResponse.OK_STATUS);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{userId}/{activationKey}")
    public void activate(
            @PathVariable("userId") String userId,
            @PathVariable("activationKey") String activationKey
    ) {
        User user = userService.findById(Long.valueOf(userId));
        if (user.getActivationKey().equals(activationKey)) {
            user.setActivated(true);
            userService.updateUser(user);
        }
    }

    private void setAuthError(BaseResponse response) {
        Error error = new Error();
        error.addError("auth", "Authentication error.");
        response.setResults(new Error(), BaseResponse.ERROR_STATUS);
    }
}
