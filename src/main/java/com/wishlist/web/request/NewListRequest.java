package com.wishlist.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class NewListRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String reason;

    @NotBlank
    private String description;

    private String date;

    @NotBlank
    private String invitation;

    @NotBlank
    private String address;

    public NewListRequest(@NotBlank String name,
                          @NotBlank String reason,
                          @NotBlank String description,
                          String date,
                          @NotBlank String invitation,
                          @NotBlank String address) {

        this.name = name;
        this.reason = reason;
        this.description = description;
        this.date = date;
        this.invitation = invitation;
        this.address = address;
    }
}
