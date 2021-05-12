package com.wishlist.social;

import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;
import com.wishlist.web.request.converter.UserConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String COMMA = ",";
    private static final String PICTURE = "picture";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";

    private final String[] requestKeys = new String[]{PICTURE, FIRST_NAME, LAST_NAME};

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public CustomOAuth2Service(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest client) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User;

        String userInfoEndpointUri = extendRequestUri(client);
        String userNameAttributeName = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity <Map>response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
            oAuth2User = new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
        } else {
            // TODO add readable error
            throw new OAuth2AuthenticationException(new OAuth2Error("error"));
        }

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2User);
        createSocialUser(customOAuth2User);

        return customOAuth2User;
    }

    private String extendRequestUri(OAuth2UserRequest client) {
        StringBuilder builder = new StringBuilder(client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri());
        for (String key : requestKeys) {
            addKey(builder, key);
        }
        return builder.toString();
    }

    private void addKey(StringBuilder builder, String key) {
        if (builder.indexOf(key) == -1) {
            builder.append(COMMA).append(key);
        }
    }

    public User createSocialUser(CustomOAuth2User socialUser) {
        final User detachedUser = userConverter.convert(socialUser);
        detachedUser.setProvider(Provider.FACEBOOK);
        return this.userRepository.save(Objects.requireNonNull(detachedUser));
    }
}
