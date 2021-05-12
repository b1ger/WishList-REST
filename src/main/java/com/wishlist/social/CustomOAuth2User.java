package com.wishlist.social;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PICTURE = "picture";

    private final OAuth2User oAuth2User;

    public CustomOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    public String getId() {
        return "";
    }

    public String getEmail() {
        return getAttribute(EMAIL);
    }

    public String getFirstName() {
        return getAttribute(FIRST_NAME);
    }

    public String getLastName() {
        return getAttribute(LAST_NAME);
    }

    public String getPhotoUrl() {
        return getAttribute(PICTURE);
    }

    private String getAttribute(String attribute) {
        String result;
        if (attribute.equals(PICTURE)) {
            Map<String, Map<String, String>> data = (Map<String, Map<String, String>>) oAuth2User.getAttributes().get(PICTURE);
            result = data.get("data").get("url");
        } else {
            result = oAuth2User.getAttributes().get(attribute).toString();
        }
        return result;
    }
}
