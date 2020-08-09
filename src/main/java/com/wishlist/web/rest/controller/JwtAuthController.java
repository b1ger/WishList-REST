package com.wishlist.web.rest.controller;

import com.wishlist.util.JwtTokenUtil;
import com.wishlist.web.request.JwtRequest;
import com.wishlist.web.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@CrossOrigin
public class JwtAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthController(
            AuthenticationManager authenticationManager1,
            JwtTokenUtil jwtTokenUtil1,
            UserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager1;
        this.jwtTokenUtil = jwtTokenUtil1;
        this.userDetailsService = userDetailsService;
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
