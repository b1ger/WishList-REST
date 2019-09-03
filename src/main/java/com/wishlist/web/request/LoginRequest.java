package com.wishlist.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.wishlist.web.request.UserRequest.MAX_PASSWORD_LENGTH;
import static com.wishlist.web.request.UserRequest.MIN_PASSWORD_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginRequest {
    @NotBlank @Email private String email;
    @NotBlank @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH) private String password;
}
