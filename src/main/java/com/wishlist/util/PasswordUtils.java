package com.wishlist.util;

import com.wishlist.web.request.UserRequest;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

@NoArgsConstructor
public class PasswordUtils {

    public static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= UserRequest.MIN_PASSWORD_LENGTH &&
                password.length() <= UserRequest.MAX_PASSWORD_LENGTH;
    }
}
