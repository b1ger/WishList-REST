package com.wishlist.web.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private String status;

    public JwtResponse(String jwttoken, String status) {
        this.jwttoken = jwttoken;
        this.status = status;
    }
}
