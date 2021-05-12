package com.wishlist.web.ui.controller;

import com.wishlist.exception.EmailAlreadyUsedException;
import com.wishlist.model.Subscribition;
import com.wishlist.model.User;
import com.wishlist.service.EmailService;
import com.wishlist.service.UserService;
import com.wishlist.util.JwtTokenUtil;
import com.wishlist.web.request.LoginRequest;
import com.wishlist.web.request.UserRequest;
import com.wishlist.web.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@CrossOrigin
@Controller
@Slf4j
public class UserController {

    private static final String LOGIN_FORM_URL = "/login/login";

    private final UserService userService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(
            UserService userService,
            EmailService emailService,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil
    ) {
        this.userService = userService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/register")
    public String register(@Valid @ModelAttribute UserRequest userRequest,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "/user/signin";
        } else {
            try {
                User user = userService.createUser(userRequest, true);
            } catch (EmailAlreadyUsedException ex) {
                bindingResult.rejectValue("email", "userRequest.email", "This email is already used.");
                return "/user/signin";
            }
        }

        return "/user/confirm";
    }

    @PostMapping(value = "/login")
    public String getUser(@Valid @ModelAttribute LoginRequest loginRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.error(error.toString());
            });
            return LOGIN_FORM_URL;
        } else {
            try{
                authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            } catch (Exception ignored) {
                model.addAttribute("authError", true);
                return LOGIN_FORM_URL;
            }
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);

        }

        return null;
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

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException ex) {
            throw new Exception("USER_DISABLED", ex);
        } catch (BadCredentialsException ex) {
            throw new Exception("BAD_CREDENTIALS", ex);
        }
    }
}
