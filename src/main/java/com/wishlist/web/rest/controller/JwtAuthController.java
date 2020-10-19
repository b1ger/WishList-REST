package com.wishlist.web.rest.controller;

import com.wishlist.model.User;
import com.wishlist.service.UserService;
import com.wishlist.util.JwtTokenUtil;
import com.wishlist.util.PasswordUtils;
import com.wishlist.web.request.JwtRequest;
import com.wishlist.web.request.SocialUserRequest;
import com.wishlist.web.response.BaseResponse;
import com.wishlist.web.response.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class JwtAuthController {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public JwtAuthController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil1,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil1;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {

        try{
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (Exception ignored) {
            return ResponseEntity.ok(new JwtResponse(null, "ERROR"));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, "OK"));
    }

    @RequestMapping(value = "/signin/social", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> signInSocial(@RequestBody SocialUserRequest socialUser) {
        BaseResponse response = new BaseResponse();
        Map<String, Object> results = new HashMap<>();

        String generatedPassword = PasswordUtils.shakePasswordFromSocial(socialUser);
        try {
            authenticate(socialUser.getEmail(), generatedPassword);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(socialUser.getEmail());
            final String jwtToken = jwtTokenUtil.generateToken(userDetails);
            final User user = userService.findByEmail(socialUser.getEmail());

            results.put("user", user);
            results.put("jwtToket", jwtToken);

            response.setResults(results);
            response.setStatus(BaseResponse.OK_STATUS);

        } catch (Exception ex) {

            logger.debug(ex.getMessage());

            final User user = userService.createSocialUser(socialUser);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final String jwtToken = jwtTokenUtil.generateToken(userDetails);

            results.put("user", user);
            results.put("jwtToket", jwtToken);

            response.setResults(results);
            response.setStatus(BaseResponse.OK_STATUS);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
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
