package com.wishlist.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ListRequest {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String reason;

    @NotBlank
    private String description;

    private String date;

    private String time;

    @NotBlank
    private String invitation;

    private String address;

    public ListRequest(
            @NotBlank String name,
            @NotBlank String reason,
            @NotBlank String description,
            String date,
            String time,
            @NotBlank String invitation,
            String address
    ) {
        this.name = name;
        this.reason = reason;
        this.description = description;
        this.date = date;
        this.time = time;
        this.invitation = invitation;
        this.address = address;
    }
}
