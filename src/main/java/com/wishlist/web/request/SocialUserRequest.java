package com.wishlist.web.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SocialUserRequest {

    private String provider;
    private String id;
    private String email;
    private String name;
    private String photoUrl;
    private String firstName;
    private String lastName;
    private String authToken;
    private String idToken;
    private String authorizationCode;
    private Object response;

    public SocialUserRequest(String provider, String id, String email, String name, String photoUrl, String firstName, String lastName, String authToken, String idToken, String authorizationCode, Object response) {
        this.provider = provider;
        this.id = id;
        this.email = email;
        this.name = name;
        this.photoUrl = photoUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authToken = authToken;
        this.idToken = idToken;
        this.authorizationCode = authorizationCode;
        this.response = response;
    }
}
