package com.wishlist.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewUserRequest {

    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 15;

    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank @Email private String email;
    @NotBlank @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH) private String password;

    public NewUserRequest(@NotBlank String firstName,
                          @NotBlank String lastName,
                          @NotBlank @Email String email,
                          @NotBlank @Size(
                                  min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH
                          ) String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
