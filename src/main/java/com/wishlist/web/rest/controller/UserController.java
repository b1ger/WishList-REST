package com.wishlist.web.rest.controller;

import com.wishlist.exception.EmailAlreadyUsedException;
import com.wishlist.model.Subscribition;
import com.wishlist.model.User;
import com.wishlist.service.EmailService;
import com.wishlist.service.UserService;
import com.wishlist.web.request.UserRequest;
import com.wishlist.web.rest.BaseResponse;
import com.wishlist.web.rest.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/apiwl")
public class UserController {

    private UserService userService;
    private EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/user/register")
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
            } catch (EmailAlreadyUsedException ex) {
                Error errors = new Error();
                errors.addError("email", "This email already used.");
                response.setResults(errors, BaseResponse.ERROR_STATUS);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/user/login")
    public ResponseEntity<BaseResponse> login(Principal user) {
        return new ResponseEntity<>(new BaseResponse(user, BaseResponse.OK_STATUS), HttpStatus.OK);
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
}
