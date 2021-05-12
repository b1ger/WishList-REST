package com.wishlist.util;

import com.wishlist.social.CustomOAuth2User;
import com.wishlist.web.request.UserRequest;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

import java.util.Base64;

@NoArgsConstructor
public class PasswordUtils {

    public static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= UserRequest.MIN_PASSWORD_LENGTH &&
                password.length() <= UserRequest.MAX_PASSWORD_LENGTH;
    }

    public static String shakePasswordFromSocial(CustomOAuth2User socialUser) {
        return Base64.getEncoder().encodeToString((socialUser.getEmail() + ":" + socialUser.getId()).getBytes());
    }
}
