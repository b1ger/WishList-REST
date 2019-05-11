package com.wishlist.util;

import com.wishlist.web.request.NewUserRequest;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

@NoArgsConstructor
public class PasswordUtils {

    public static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= NewUserRequest.MIN_PASSWORD_LENGTH &&
                password.length() <= NewUserRequest.MAX_PASSWORD_LENGTH;
    }
}
